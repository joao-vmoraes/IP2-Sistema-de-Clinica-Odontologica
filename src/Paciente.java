import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private List<Agendamento> historicoProcedimentos = new ArrayList<>(); 
    private boolean possuiPagamentoPendente; 

    //CONSTRUTOR
    public Paciente(String nome, String cpf, String telefone, String email, String endereco) {
        super(nome, cpf, telefone, email, endereco);
        this.possuiPagamentoPendente = false; 
    }

    public List<Agendamento> getHistoricoProcedimentos() {
        return historicoProcedimentos;
    }

    public void adicionarHistorico(Agendamento agendamento) {
        this.historicoProcedimentos.add(agendamento);
    }

    public boolean isPossuiPagamentoPendente() {
        return possuiPagamentoPendente;
    }

    public void setPossuiPagamentoPendente(boolean possuiPagamentoPendente) {
        this.possuiPagamentoPendente = possuiPagamentoPendente;
    }

    public void atualizarInformacoes(String telefone, String email, String endereco) {
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
    }
}