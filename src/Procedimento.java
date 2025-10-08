public class Procedimento {
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;
    private String status;
    private Dentista dentistaExecutor;
    private Paciente pacienteAlvo;

    //CONSTRUTOR
    public Procedimento(String descricao, double preco, int duracaoEmMinutos) {
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.status = "Planejado";
    }

    //MÉTODOS
    public double getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public String getStatus() {
        return status;
    }

    public void setDentistaExecutor(Dentista dentistaExecutor) {
        this.dentistaExecutor = dentistaExecutor;
    }

    public void setPacienteAlvo(Paciente pacienteAlvo) {
        this.pacienteAlvo = pacienteAlvo;
    }

    //atualizar status (REQ12, REQ26)
    public void atualizarStatus(String novoStatus) {
        if (this.status.equals("Concluído") && novoStatus.equals("Cancelado")) {
            System.out.println("Erro: Procedimento concluído não pode ser cancelado sem registro de justificativa.");
            return;
        }
        this.status = novoStatus;
    }
}