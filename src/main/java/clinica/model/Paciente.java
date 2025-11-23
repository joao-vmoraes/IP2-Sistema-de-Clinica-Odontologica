package clinica.model;

public class Paciente extends Pessoa {
    private boolean possuiPagamentoPendente;

    //CONSTRUTOR
    public Paciente(String nome, String cpf, String telefone, String email, String endereco) {
        super(nome, cpf, telefone, email, endereco);
        this.possuiPagamentoPendente = false;
    }

    //Getters e Setters
    public boolean getPossuiPagamentoPendente() {
        return this.possuiPagamentoPendente;
    }

    //Métodos
    public void setPossuiPagamentoPendente(boolean possuiPagamentoPendente) {
        this.possuiPagamentoPendente = possuiPagamentoPendente;
    }

    public void atualizarInformacoes(String telefone, String email, String endereco) {
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
    }

}