import java.time.LocalDateTime;

public class Consulta {
    private Paciente paciente;
    private Dentista dentista;
    private Procedimento procedimento;
    private StatusConsulta statusConsulta;
    private LocalDateTime dataHora;

    //Construtor
    public Consulta(LocalDateTime dataHora, StatusConsulta statusConsulta, Dentista dentista, Paciente paciente, Procedimento procedimento) {
        this.dataHora = dataHora;
        this.statusConsulta = statusConsulta.PLANEJADO;
        this.dentista = dentista;
        this.paciente = paciente;
        this.procedimento = procedimento;
    }

    //Getters e Setters
    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    //Métodos
    public void atualizarConsulta(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora,  StatusConsulta statusConsulta) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.statusConsulta = statusConsulta;
    }
    //Os Setters são redundantes, atualizarConsulta faz todo o trabalho deles.
    //Vou deixar pois eu os vejo sendo úteis dependendo do contexto
}
