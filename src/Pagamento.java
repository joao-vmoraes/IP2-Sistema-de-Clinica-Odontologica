public class Pagamento {
    private double valor;
    private String metodo;
    private boolean confirmado;

    //CONSTRUTOR
    public Pagamento(double valor, String metodo) {
        this.valor = valor;
        this.metodo = metodo;
        this.confirmado = false;
    }

    //MÉTODOS
    public void confirmarPagamento() {
        this.confirmado = true;
    }
    public boolean isConfirmado() {
        return confirmado;
    }
}
