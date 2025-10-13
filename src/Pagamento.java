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

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Procedimento getReferenciaProcedimento() {
        return referenciaProcedimento;
    }

    public void setReferenciaProcedimento(Procedimento referenciaProcedimento) {
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