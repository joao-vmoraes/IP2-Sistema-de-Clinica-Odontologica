public class Procedimento {
    private String nome;
    private double preco;
    private int duracaoEmMinutos;
    private Dentista dentistaExecutor;
    private Paciente pacienteAlvo;

    //CONSTRUTOR
    public Procedimento(String nome, double preco, int duracaoEmMinutos, Dentista dentista) {
        this.nome = nome;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.dentistaExecutor = dentista;
    }

    //MÉTODOS
    public double getPreco() {
        return this.preco;
    }

    public String getNome() {
        return this.nome;
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