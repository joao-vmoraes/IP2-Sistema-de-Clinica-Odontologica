import java.time.LocalDateTime;

enum MetodoPagamento {
    Dinehiro,
    Cartao,
    PIX,
}

public class Pagamento {
    private double valor;
    private MetodoPagamento metodo;
    private boolean confirmado;
    private LocalDateTime dataPagamento;
    private Procedimento referenciaProcedimento;

    //CONSTRUTOR
    public Pagamento(double valor, MetodoPagamento metodo, Procedimento referenciaProcedimento) {
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