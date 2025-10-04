
public class Procedimento {
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;
    private String status;

    //CONSTRUTOR
    public Procedimento(String descricao, double preco, int duracaoEmMinutos) {
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.status = "Planejado";
    }

    //MÉTODOS
    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }
    public String getStatus() {
        return status;
    }
}
