package clinica.model;

import clinica.enums.StatusAgendamento;
import java.time.LocalDateTime;

public class Agendamento {
    private Integer id; // Opcional, mas útil
    private Paciente paciente;
    private Dentista dentista;
    private Procedimento procedimento;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String sala;
    private boolean pago; // NOVO CAMPO: Status financeiro deste agendamento específico

    public Agendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String sala) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.sala = sala;
        this.status = StatusAgendamento.PLANEJADO;
        this.pago = false; // Todo agendamento começa como não pago
    }

    // --- Getters e Setters ---

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    // Sobrescreve toString para facilitar exibição em ComboBoxes (usado no PagamentoController)
    @Override
    public String toString() {
        // Exemplo: "10/12 - Canal (João Silva)"
        return dataHora.getDayOfMonth() + "/" + dataHora.getMonthValue() + " - " +
                (procedimento != null ? procedimento.getNome() : "Consulta") +
                " (" + paciente.getNome() + ")";
    }
}