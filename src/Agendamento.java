import java.time.LocalDateTime;

public class Agendamento extends Atendimento {
    private LocalDateTime dataHora;
    private Pagamento pagamento;
    private String salaAtendimento;
    private boolean cancelado;
    private StatusConsulta statusConsulta;
    private String justificativaCancelamento;
    

    //CONSTRUTOR
    public Agendamento(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora, String salaAtendimento, StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.salaAtendimento = salaAtendimento;
        this.cancelado = false;
    }

    //GETTERS E SETTERS
    public StatusConsulta getStatusConsulta() {
        return this.statusConsulta;
    }
    public void setStatusConsulta(StatusConsulta statusConsulta) {
        this.statusConsulta = statusConsulta;
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
    
    public void atualizarConsulta(Paciente paciente, Dentista dentista, Procedimento procedimento, LocalDateTime dataHora,  StatusConsulta statusConsulta) {
        this.paciente = paciente;
        this.dentista = dentista;
        this.procedimento = procedimento;
        this.dataHora = dataHora;
        this.statusConsulta = statusConsulta;
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
    public void atualizarStatus(StatusConsulta novoStatus) {
        if (this.statusConsulta.equals(statusConsulta.CONCLUIDO) || novoStatus.equals(statusConsulta.CANCELADO)) {
            System.out.println("Erro: Procedimento concluído não pode ser cancelado.");
            return;
        }
        this.statusConsulta = novoStatus;
    }
}