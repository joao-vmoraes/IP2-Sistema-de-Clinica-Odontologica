import java.time.LocalDateTime;

public class Consulta extends Atendimento {
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
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public StatusConsulta getStatusConsulta() {
        return this.statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public Procedimento getProcedimento() {
        return this.procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public Dentista getDentista() {
        return this.dentista;
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
        //Os Setters são redundantes, atualizarConsulta faz todo o trabalho deles.
        //Vou deixar pois eu os vejo sendo úteis dependendo do contexto
    }

    //atualizar status (REQ12, REQ26)
    public void atualizarStatus(StatusConsulta novoStatus) {
        if (this.statusConsulta.equals(statusConsulta.CONCLUIDO) || novoStatus.equals(statusConsulta.CANCELADO)) {
            System.out.println("Erro: Procedimento concluído não pode ser cancelado.");
            return;
        }
        this.statusConsulta = novoStatus;
    }
}
