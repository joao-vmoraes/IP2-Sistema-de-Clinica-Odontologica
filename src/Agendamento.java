import java.time.LocalDateTime;

public class Agendamento{
    private Integer id;
    private Paciente paciente;
    private Dentista dentista;
    private LocalDateTime dataHora;
    private StatusAgendamento status;
    private String sala;

    public Agendamento(Paciente paciente, Dentista dentista, LocalDateTime dataHora, String sala) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.dataHora = dataHora;
        this.sala = sala;
        this.status = StatusAgendamento.PLANEJADO;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
}