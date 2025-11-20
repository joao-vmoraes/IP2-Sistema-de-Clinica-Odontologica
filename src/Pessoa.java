import java.util.Objects;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String email;
    protected String endereco;
    protected String telefone;

    public Pessoa(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters e Setters mantidos...
    public String getNome() { return this.nome; }
    public String getCpf() { return this.cpf; }
    public String getEmail() { return this.email; }
    public String getTelefone() { return this.telefone; }
    public String getEndereco() { return this.endereco; }
    public void setEmail(String email) { this.email = email; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // IMPORTANTE: Para que o .contains() e .remove() funcionem nas listas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(cpf, pessoa.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }
}