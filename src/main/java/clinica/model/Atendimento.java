package clinica.model;

import java.time.LocalDateTime;

import clinica.enums.StatusAgendamento;

public class Atendimento {
    private Agendamento agendamentoOriginal; // Liga ao agendamento
    private Procedimento procedimentoRealizado;
    private String anotacoesDentista; // Prontuário/Evolução
    private LocalDateTime horaInicioReal;
    private LocalDateTime horaFimReal;

    public Atendimento(Agendamento agendamento) {
        this.agendamentoOriginal = agendamento;
        this.horaInicioReal = LocalDateTime.now();
    }

    public void finalizarAtendimento(String anotacoes, Procedimento procedimento) {
        this.anotacoesDentista = anotacoes;
        this.procedimentoRealizado = procedimento;
        this.horaFimReal = LocalDateTime.now();
        this.agendamentoOriginal.setStatus(StatusAgendamento.CONCLUIDO);
        this.agendamentoOriginal.AnotarDataConclusao();
    }

    public Agendamento getAgendamentoOriginal() {
        return agendamentoOriginal;
    }
    public void setAgendamentoOriginal(Agendamento agendamentoOriginal) {
        this.agendamentoOriginal = agendamentoOriginal;
    }
    public LocalDateTime getHoraFimReal() {
        return horaFimReal;
    }
    public void setHoraFimReal(LocalDateTime horaFimReal) {
        this.horaFimReal = horaFimReal;
    }
    public LocalDateTime getHoraInicioReal() {
        return horaInicioReal;
    }
    public void setHoraInicioReal(LocalDateTime horaInicioReal) {
        this.horaInicioReal = horaInicioReal;
    }
    public String getAnotacoesDentista() {
        return anotacoesDentista;
    }
    public void setAnotacoesDentista(String anotacoesDentista) {
        this.anotacoesDentista = anotacoesDentista;
    }
    public Procedimento getProcedimentoRealizado() {
        return procedimentoRealizado;
    }
    public void setProcedimentoRealizado(Procedimento procedimentoRealizado) {
        this.procedimentoRealizado = procedimentoRealizado;
    }
}