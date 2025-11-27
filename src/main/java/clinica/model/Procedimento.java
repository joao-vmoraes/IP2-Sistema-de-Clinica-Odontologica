package clinica.model;

public class Procedimento {
    private String nome;
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;

    //CONSTRUTORES
    public Procedimento(String nome, String descricao, double preco, int duracaoEmMinutos) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public Procedimento(String nome, double preco, int duracaoEmMinutos) {
        this.nome = nome;
        this.descricao = null;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    //MÉTODOS
    public double getPreco() {
        return this.preco;
    }

    public String getNome() {
        return this.nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public int getDuracaoEmMinutos() {
        return this.duracaoEmMinutos;
    }
}