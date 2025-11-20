import java.util.Objects;

public class Procedimento {
    private String descricao;
    private double preco;
    private int duracaoEmMinutos;
    private Dentista dentistaExecutor;
    private Paciente pacienteAlvo;

    public Procedimento(String descricao, double preco, int duracaoEmMinutos, Dentista dentista) {
        this.descricao = descricao;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
        this.dentistaExecutor = dentista;
    }

    public double getPreco() { return this.preco; }
    public String getDescricao() { return this.descricao; }
    public int getDuracaoEmMinutos() { return this.duracaoEmMinutos; }
    public void setDentistaExecutor(Dentista dentistaExecutor) { this.dentistaExecutor = dentistaExecutor; }
    public void setPacienteAlvo(Paciente pacienteAlvo) { this.pacienteAlvo = pacienteAlvo; }

    // IMPORTANTE: Para evitar duplicatas no catálogo
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedimento that = (Procedimento) o;
        return Double.compare(that.preco, preco) == 0 && 
               Objects.equals(descricao, that.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, preco);
    }
}