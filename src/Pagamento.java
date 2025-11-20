import java.time.LocalDateTime;

public class Pagamento {
    private double valor;
    private MetodoPagamento metodo;
    private boolean confirmado;
    private LocalDateTime dataPagamento;
    private Procedimento referenciaProcedimento;

    public Pagamento(double valor, MetodoPagamento metodo, Procedimento referenciaProcedimento) {
        this.valor = valor;
        this.metodo = metodo;
        this.confirmado = false;
        this.referenciaProcedimento = referenciaProcedimento;
    }

    // Correção aqui: Retorna o Enum, não String
    public MetodoPagamento getMetodo() {
        return metodo;
    }

    // Correção aqui: Recebe o Enum, não String
    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public void setValor(double valor) {
        this.valor = valor;
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

    public boolean isConfirmado() {
        return this.confirmado;
    }
    
    public double getValor() {
        return this.valor;
    }

    public void confirmarPagamento() {
        this.confirmado = true;
        this.dataPagamento = LocalDateTime.now(); // Boa prática registrar a data ao confirmar
    }

    public void realizarPagamento() {
        if(!isConfirmado()) {
            System.err.println("Erro ao realizar pagamento. Pagamento nao confirmado.");
            return;
        }
        this.dataPagamento = LocalDateTime.now();
        System.out.println("Pagamento realizado com sucesso.");
    }
}