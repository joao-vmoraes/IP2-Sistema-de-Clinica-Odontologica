import java.time.LocalDateTime;

public class Agendamento {
    private Paciente paciente;
    private Dentista dentista;
    private Procedimento procedimento;
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
        return dataHora;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public Dentista getDentista() {
        return dentista;
    }
    public Procedimento getProcedimento() {
        return procedimento;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public String getSalaAtendimento() {
        return salaAtendimento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    
    public boolean isCancelado() { 
        return cancelado;
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