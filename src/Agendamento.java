import java.time.LocalDateTime;

public class Agendamento{
    protected Paciente paciente;
    protected Dentista dentista;
    private Procedimento procedimento;
    private LocalDateTime dataHora;
    private Pagamento pagamento;
    private String salaAtendimento;
    private boolean cancelado;
    private StatusAgendamento statusAgendamento;
    private String justificativaCancelamento;
    

    //CONSTRUTOR
    public Agendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String salaAtendimento, StatusAgendamento statusAgendamento) {
        this.statusAgendamento = statusAgendamento;
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.salaAtendimento = salaAtendimento;
        this.cancelado = false;
    }

    //GETTERS E SETTERS
    public StatusAgendamento getStatusConsulta() {
        return this.statusAgendamento;
    }
    public void setStatusConsulta(StatusAgendamento statusConsulta) {
        this.statusAgendamento = statusConsulta;
    }
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
      public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
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
    
    public void atualizarConsulta(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora,  StatusAgendamento statusConsulta) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.statusAgendamento = statusConsulta;
        //Os Setters são redundantes, atualizarConsulta faz todo o trabalho deles.
        //Vou deixar pois eu os vejo sendo úteis dependendo do contexto
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
    public void atualizarStatus(StatusAgendamento novoStatus) {
        if (this.statusAgendamento.equals(StatusAgendamento.CONCLUIDO) || novoStatus.equals(statusAgendamento.CANCELADO)) {
            System.out.println("Erro: Procedimento concluído não pode ser cancelado.");
            return;
        }
        this.statusAgendamento = novoStatus;
    }
}