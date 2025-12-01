package clinica.model;

import java.time.LocalDateTime;

import clinica.enums.MetodoPagamento;

public class Pagamento {
    private double valor;
    private MetodoPagamento metodo;
    private boolean confirmado;
    private LocalDateTime dataPagamento;
    private Agendamento referenciaAgendamento;

    public Pagamento(double valor, MetodoPagamento metodo, Agendamento referenciaAgendamento) {
        this.valor = valor;
        this.metodo = metodo;
        this.confirmado = false;
        this.referenciaAgendamento = referenciaAgendamento;
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

    public Agendamento getReferenciaAgendamento() {
        return referenciaAgendamento;
    }

    public void setReferenciaAgendamento(Agendamento referenciaAgendamento) {
        this.referenciaAgendamento = referenciaAgendamento;
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

    public void realizarPagamento() {
        if(!isConfirmado())
        {
            System.err.println("Erro ao realizar pagamento. model.Pagamento nao confirmado.");
            return;
        }


        this.dataPagamento = LocalDateTime.now();
        System.err.println("model.Pagamento realizado");
    }
}