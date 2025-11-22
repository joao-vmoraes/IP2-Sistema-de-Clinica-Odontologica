package clinica.controller;

import java.time.LocalDateTime;
import java.util.List;

import clinica.enums.StatusAgendamento;
import clinica.model.*;
import clinica.repository.*;

public class ClinicaManager {
    // Atributos são os Repositórios (Composição)
    private AgendamentoRepositorio agendamentoRepo;
    private DentistaRepositorio dentistaRepo;
    private PacienteRepositorio pacienteRepo;

    // Construtor com Injeção de Dependência
    public ClinicaManager(AgendamentoRepositorio agendamentoRepo,
                          DentistaRepositorio dentistaRepo,
                          PacienteRepositorio pacienteRepo) {
        this.agendamentoRepo = agendamentoRepo;
        this.dentistaRepo = dentistaRepo;
        this.pacienteRepo = pacienteRepo;
    }

    public boolean marcarAgendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String sala) {
        // REQ24: Bloquear agendamento se tiver pagamento pendente
        if (paciente.getPossuiPagamentoPendente()) {
            System.out.println("Erro: model.Paciente possui pagamentos pendentes. model.Agendamento bloqueado.");
            return false;
        }

        // REQ23: Verificar disponibilidade
        if (!verificarDisponibilidadeDentista(dentista, dataHora, procedimento.getDuracaoEmMinutos())) {
            System.out.println("Erro: model.Dentista indisponível no horário solicitado.");
            return false;
        }

        // Cria e salva
        Agendamento novoAgendamento = new Agendamento(paciente, dentista, procedimento, dataHora, sala);
        // Se precisar associar o procedimento ao agendamento, sua classe model.Agendamento precisa de um campo 'procedimento'.
        // Baseado no seu arquivo model.Agendamento.java, não vi o campo procedimento no construtor,
        // mas vou assumir que você pode querer adicionar ou que a lógica de negócio associa depois.

        agendamentoRepo.salvar(novoAgendamento);
        System.out.println("Consulta agendada com sucesso para " + dataHora);
        return true;
    }

    public void registrarPagamento(Pagamento pagamento) {
        // Idealmente teria um PagamentoRepositorio, mas se não tiver, pode processar aqui
        pagamento.confirmarPagamento();
        System.out.println("model.Pagamento registrado: " + pagamento.getValor());
    }

    // Lógica de disponibilidade ajustada para buscar no Repositório
    private boolean verificarDisponibilidadeDentista(Dentista dentista, LocalDateTime dataHora, int duracaoMinutos) {
        // 1. Verificar folgas e ausências do objeto model.Dentista
        if (dentista.getDiasDeFolga().contains(dataHora.getDayOfWeek()) ||
                dentista.estaAusente(dataHora.toLocalDate())) {
            return false;
        }

        // 2. Verificar horário de trabalho
        if (dataHora.toLocalTime().isBefore(dentista.getHorarioTrabalhoInicio()) ||
                dataHora.toLocalTime().plusMinutes(duracaoMinutos).isAfter(dentista.getHorarioTrabalhoFim())) {
            return false;
        }

        // 3. Verificar conflito com outros agendamentos NO REPOSITÓRIO
        List<Agendamento> todosAgendamentos = agendamentoRepo.listarTodos();
        LocalDateTime fimNovo = dataHora.plusMinutes(duracaoMinutos);

        for (Agendamento a : todosAgendamentos) {
            // Filtra apenas agendamentos deste dentista que não estão cancelados
            if (a.getDentista().getCpf().equals(dentista.getCpf()) && a.getStatus() != StatusAgendamento.CANCELADO) {

                // Precisamos saber a duração do agendamento existente.
                // Como model.Agendamento.java não tem o procedimento direto no código que você mandou,
                // vou assumir uma duração padrão de 30 min ou você deve adicionar 'model.Procedimento' em 'model.Agendamento'.
                int duracaoExistente = 30;

                LocalDateTime inicioExistente = a.getDataHora();
                LocalDateTime fimExistente = inicioExistente.plusMinutes(duracaoExistente);

                // Lógica de intersecção de horários
                if (dataHora.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                    return false; // Colisão encontrada
                }
            }
        }
        return true;
    }
}