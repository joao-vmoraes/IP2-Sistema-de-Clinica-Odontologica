import java.time.LocalDateTime;

public class Pagamento {
    private double valor;
    private String metodo;
    private boolean confirmado;
    private LocalDateTime dataPagamento;
    private Procedimento referenciaProcedimento;

    //CONSTRUTOR
    public Pagamento(double valor, String metodo, Procedimento referenciaProcedimento) {
        this.valor = valor;
        this.metodo = metodo;
        this.confirmado = false;
        this.referenciaProcedimento = referenciaProcedimento;
    }

    //MÉTODOS
    public boolean isConfirmado() {
        return confirmado;
    }
    public double getValor() {
        return valor;
    }

    public void confirmarPagamento() {
        this.confirmado = true;
        this.dataPagamento = LocalDateTime.now();
    }
}