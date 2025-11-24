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

        // Regra de bloqueio global removida para permitir gestão financeira por item.
        // O status de pagamento será gerido individualmente em cada Agendamento.

        // REQ23: Verificar disponibilidade do dentista
        if (!verificarDisponibilidadeDentista(dentista, dataHora, procedimento.getDuracaoEmMinutos())) {
            System.out.println("Erro: Dentista indisponível no horário solicitado.");
            return false;
        }

        // Cria o agendamento (por padrão, ele nasce com pago = false)
        Agendamento novoAgendamento = new Agendamento(paciente, dentista, procedimento, dataHora, sala);

        // Salva no repositório
        agendamentoRepo.salvar(novoAgendamento);

        System.out.println("Consulta agendada com sucesso para " + dataHora);
        return true;
    }

    private boolean verificarDisponibilidadeDentista(Dentista dentista, LocalDateTime dataHora, int duracaoMinutos) {
        // 1. Verificar horário de trabalho do dentista (início e fim de expediente)
        if (dataHora.toLocalTime().isBefore(dentista.getHorarioTrabalhoInicio()) ||
                dataHora.toLocalTime().plusMinutes(duracaoMinutos).isAfter(dentista.getHorarioTrabalhoFim())) {
            return false;
        }

        // 2. Verificar conflito com outros agendamentos já existentes NO REPOSITÓRIO
        List<Agendamento> todosAgendamentos = agendamentoRepo.listarTodos();
        LocalDateTime fimNovo = dataHora.plusMinutes(duracaoMinutos);

        for (Agendamento a : todosAgendamentos) {
            // Checa apenas agendamentos deste mesmo dentista que não foram cancelados
            if (a.getDentista().getCpf().equals(dentista.getCpf()) &&
                    a.getStatus() != StatusAgendamento.CANCELADO) {

                // Recupera a duração do procedimento agendado
                int duracaoExistente = a.getProcedimento().getDuracaoEmMinutos();

                LocalDateTime inicioExistente = a.getDataHora();
                LocalDateTime fimExistente = inicioExistente.plusMinutes(duracaoExistente);

                // Lógica de intersecção de horários:
                // Se o novo começa antes do existente terminar E o novo termina depois do existente começar
                if (dataHora.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                    return false; // Conflito encontrado
                }
            }
        }
        return true;
    }
}