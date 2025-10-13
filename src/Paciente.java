import java.util.ArrayList;
import java.util.List;

public class Paciente extends Pessoa {
    private List<Agendamento> agenda = new ArrayList<>(); 
    private List<Consulta> historicoProcedimentos = new ArrayList<>(); 
    private boolean possuiPagamentoPendente; 

    //CONSTRUTOR
    public Paciente(String nome, String cpf, String telefone, String email, String endereco) {
        super(nome, cpf, telefone, email, endereco);
        this.possuiPagamentoPendente = false; 
    }

    //Getters e Setters
    public List<Consulta> getHistoricoProcedimentos() {
        return this.historicoProcedimentos;
    }

    public List<Agendamento> getAgenda() {
        return this.agenda;
    }

    public boolean temPagamentoPendente() {
        return this.possuiPagamentoPendente;
    }

    //Métodos
    public void setPossuiPagamentoPendente(boolean possuiPagamentoPendente) {
        this.possuiPagamentoPendente = possuiPagamentoPendente;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        this.agenda.add(agendamento);
    }
    
    public void removerAgendamento(Agendamento agendamento) {
        this.agenda.remove(agendamento);
    }

    public void AdicionarNoHistorico(Consulta consulta) {
        this.historicoProcedimentos.add(consulta);
    }

    public void atualizarInformacoes(String telefone, String email, String endereco) {
        this.setTelefone(telefone);
        this.setEmail(email);
        this.setEndereco(endereco);
    }

}