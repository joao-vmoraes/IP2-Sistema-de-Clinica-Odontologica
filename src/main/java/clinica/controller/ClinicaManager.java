package clinica.controller;

import clinica.model.Agendamento;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.enums.StatusAgendamento;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class ClinicaManager {
    private AgendamentoRepositorio agendamentoRepo;
    private DentistaRepositorio dentistaRepo;
    private PacienteRepositorio pacienteRepo;

    public ClinicaManager(AgendamentoRepositorio agendamentoRepo, DentistaRepositorio dentistaRepo, PacienteRepositorio pacienteRepo) {
        this.agendamentoRepo = agendamentoRepo;
        this.dentistaRepo = dentistaRepo;
        this.pacienteRepo = pacienteRepo;
    }

    public boolean marcarAgendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String sala) {
        // REQ24: Bloquear agendamento se tiver pagamento pendente
        // (Assumindo que você tem esse método no Paciente, se não tiver, remova o if)
        if (paciente.getPossuiPagamentoPendente()) {
            System.out.println("Erro: Paciente possui pagamentos pendentes. Agendamento bloqueado.");
            return false;
        }

        // REQ23: Verificar disponibilidade
        if (!verificarDisponibilidadeDentista(dentista, dataHora, procedimento.getDuracaoEmMinutos())) {
            System.out.println("Erro: Dentista indisponível no horário solicitado.");
            return false;
        }

        // Cria e salva o agendamento
        // Importante: O construtor de Agendamento deve aceitar Procedimento!
        Agendamento novoAgendamento = new Agendamento(paciente, dentista, procedimento, dataHora, sala);
        agendamentoRepo.salvar(novoAgendamento);

        System.out.println("Consulta agendada com sucesso para " + dataHora);
        return true;
    }

    private boolean verificarDisponibilidadeDentista(Dentista dentista, LocalDateTime dataHora, int duracaoMinutos) {
        // 1. Verificar horário de trabalho
        // Se dataHora for antes do inicio ou depois do fim do expediente
        if (dataHora.toLocalTime().isBefore(dentista.getHorarioTrabalhoInicio()) ||
                dataHora.toLocalTime().plusMinutes(duracaoMinutos).isAfter(dentista.getHorarioTrabalhoFim())) {
            return false;
        }

        // 2. Verificar conflito com outros agendamentos NO REPOSITÓRIO
        List<Agendamento> todosAgendamentos = agendamentoRepo.listarTodos();
        LocalDateTime fimNovo = dataHora.plusMinutes(duracaoMinutos);

        for (Agendamento a : todosAgendamentos) {
            // Verifica apenas agendamentos deste dentista que não estão cancelados
            if (a.getDentista().getCpf().equals(dentista.getCpf()) &&
                    a.getStatus() != StatusAgendamento.CANCELADO) {

                int duracaoExistente = a.getProcedimento().getDuracaoEmMinutos();
                LocalDateTime inicioExistente = a.getDataHora();
                LocalDateTime fimExistente = inicioExistente.plusMinutes(duracaoExistente);

                // Lógica de intersecção de horários: (InicioA < FimB) e (FimA > InicioB)
                if (dataHora.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                    return false; // Colisão encontrada
                }
            }
        }
        return true;
    }
}