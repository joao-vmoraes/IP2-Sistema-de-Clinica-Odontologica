package clinica.model;

import java.time.LocalDateTime;

import clinica.enums.MetodoPagamento;

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
    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }


    //MÉTODOS
    public boolean isConfirmado() {
        return this.confirmado;
    }
    public double getValor() {
        return this.valor;
    }

    public void confirmarPagamento() {
        this.confirmado = true;
    }

    public void realizarPagamento(/*contaDevedora, contaRecebedora*/) {
        if(!isConfirmado())
        {
            System.err.println("Erro ao realizar pagamento. model.Pagamento nao confirmado.");
            return;
        }

        //fazer a checagem pra ver se ambas as contas estão disponíveis, caso não: return

        //Realiza pagamento k
        this.dataPagamento = LocalDateTime.now();
        System.err.println("model.Pagamento realizado");
    }
}