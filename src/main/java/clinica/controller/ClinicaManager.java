package clinica.controller;

import clinica.enums.StatusAgendamento;
import clinica.model.*;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.AtendimentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.PagamentoRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class ClinicaManager {
    private AgendamentoRepositorio agendamentoRepo;
    private DentistaRepositorio dentistaRepo;
    private PacienteRepositorio pacienteRepo;
    private AtendimentoRepositorio atendimentoRepo;
    private PagamentoRepositorio pagamentoRepo;

    public ClinicaManager(AgendamentoRepositorio agendamentoRepo, DentistaRepositorio dentistaRepo, PacienteRepositorio pacienteRepo, AtendimentoRepositorio atendimentoRepo, PagamentoRepositorio pagamentoRepo) {
        this.agendamentoRepo = agendamentoRepo;
        this.dentistaRepo = dentistaRepo;
        this.pacienteRepo = pacienteRepo;
        this.atendimentoRepo = atendimentoRepo;
        this.pagamentoRepo = pagamentoRepo;
    }

    // --- AGENDAMENTOS ---

    public boolean marcarAgendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String sala) {
        if (!verificarDisponibilidadeDentista(dentista, dataHora, procedimento.getDuracaoEmMinutos())) {
            System.out.println("Erro: Dentista indisponível neste horário.");
            return false;
        }

        Agendamento novoAgendamento = new Agendamento(paciente, dentista, procedimento, dataHora, sala);
        agendamentoRepo.salvar(novoAgendamento);
        System.out.println("Agendamento realizado com sucesso para " + dataHora);
        return true;
    }

    public void cancelarAgendamento(Agendamento agendamento) {
        agendamento.setStatus(StatusAgendamento.CANCELADO);
    }

    public boolean verificarDisponibilidadeDentista(Dentista dentista, LocalDateTime dataHora, int duracaoMinutos) {
        // 1. Verificar horário de expediente
        if (dataHora.toLocalTime().isBefore(dentista.getHorarioTrabalhoInicio()) ||
                dataHora.toLocalTime().plusMinutes(duracaoMinutos).isAfter(dentista.getHorarioTrabalhoFim())) {
            return false;
        }

        // 2. Verificar conflitos
        List<Agendamento> todosAgendamentos = agendamentoRepo.listarTodos();
        LocalDateTime fimNovo = dataHora.plusMinutes(duracaoMinutos);

        for (Agendamento a : todosAgendamentos) {
            if (a.getDentista().getCpf().equals(dentista.getCpf()) &&
                    a.getStatus() != StatusAgendamento.CANCELADO) {

                int duracaoExistente = a.getProcedimento().getDuracaoEmMinutos();
                LocalDateTime inicioExistente = a.getDataHora();
                LocalDateTime fimExistente = inicioExistente.plusMinutes(duracaoExistente);

                if (dataHora.isBefore(fimExistente) && fimNovo.isAfter(inicioExistente)) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Agendamento> listarTodosAgendamentos() {
        return agendamentoRepo.listarTodos();
    }

    public List<Agendamento> buscarAgendamentosPorCpfPaciente(String cpf) {
        return agendamentoRepo.buscarPorCpfPaciente(cpf);
    }

    // --- ATENDIMENTOS ---

    public void finalizarAtendimento(Agendamento agendamento, String anotacoes) {
        Atendimento novoAtendimento = new Atendimento(agendamento);
        novoAtendimento.finalizarAtendimento(anotacoes, agendamento.getProcedimento());
        atendimentoRepo.salvar(novoAtendimento);
    }

    // --- FINANCEIRO ---

    public void registrarPagamento(Pagamento pagamento) {
        pagamento.confirmarPagamento();
        pagamentoRepo.salvar(pagamento);

        // Atualiza o agendamento vinculado
        if(pagamento.getReferenciaAgendamento() != null) {
            pagamento.getReferenciaAgendamento().setPago(true);
        }
    }

    public List<Pagamento> listarPagamentos() {
        return pagamentoRepo.listarTodos();
    }
}