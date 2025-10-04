import java.time.LocalDateTime;

public class Agendamento {
    private Paciente paciente;
    private Dentista dentista;
    private Procedimento procedimento;
    private LocalDateTime dataHora;

    //CONSTRUTOR
    public Agendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
    }

    //GETTERS
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public Dentista getDentista() {
        return dentista;
    }


}
