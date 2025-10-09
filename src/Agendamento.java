import java.time.LocalDateTime;

public class Agendamento extends Atendimento {
    private LocalDateTime dataHora;
    private Pagamento pagamento;
    private String salaAtendimento;
    private boolean cancelado;
    private String justificativaCancelamento;

    //CONSTRUTOR
    public Agendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String salaAtendimento) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.salaAtendimento = salaAtendimento;
        this.cancelado = false;
    }

    //GETTERS E SETTERS
    public LocalDateTime getDataHora() {
        return this.dataHora;
    }
    public Paciente getPaciente() {
        return this.paciente;
    }
    public Dentista getDentista() {
        return this.dentista;
    }
    public Procedimento getProcedimento() {
        return this.procedimento;
    }

    public Pagamento getPagamento() {
        return this.pagamento;
    }

    public String getSalaAtendimento() {
        return this.salaAtendimento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public boolean isCancelado() { 
        return this.cancelado;
    } 
    
    //Atualizar agendamentos (REQ09)
    public void cancelar(String justificativa) {
        this.cancelado = true;
        this.justificativaCancelamento = justificativa;
    }

    public void remarcar(LocalDateTime novaDataHora, String novaSala) {
        this.dataHora = novaDataHora;
        this.salaAtendimento = novaSala;
    }
}