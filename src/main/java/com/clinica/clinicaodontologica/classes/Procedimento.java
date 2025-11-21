package com.clinica.clinicaodontologica.classes;

public class Procedimento {
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;

    //CONSTRUTOR
    public Procedimento(String descricao, double preco, int duracaoEmMinutos) {
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
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
}