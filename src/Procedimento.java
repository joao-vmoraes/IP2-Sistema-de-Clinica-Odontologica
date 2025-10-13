public class Procedimento {
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;
    private Dentista dentistaExecutor;
    private Paciente pacienteAlvo;

    //CONSTRUTOR
    public Procedimento(String descricao, double preco, int duracaoEmMinutos, Dentista dentista) {
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.dentistaExecutor = dentista;
    }

    //MÉTODOS
    public double getPreco() {
        return this.preco;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getDuracaoEmMinutos() {
        return this.duracaoEmMinutos;
    }

    public void setDentistaExecutor(Dentista dentistaExecutor) {
        this.dentistaExecutor = dentistaExecutor;
    }

    public void setPacienteAlvo(Paciente pacienteAlvo) {
        this.pacienteAlvo = pacienteAlvo;
    }
}