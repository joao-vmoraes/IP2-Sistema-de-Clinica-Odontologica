import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dentista extends Pessoa {
    private String especialidade;
    private StatusDentista status;
    private List<Agendamento> agenda = new ArrayList<>();
    private List<Agendamento> historicoConsultas = new ArrayList<>();
    private LocalTime horarioTrabalhoInicio; 
    private LocalTime horarioTrabalhoFim;
    private List<DayOfWeek> diasDeFolga = new ArrayList<>();
    private Map<LocalDate, String> periodosAusencia = new HashMap<>();

    //CONSTRUTOR
    public Dentista(String nome, String cpf, String telefone, String email, String endereco, String especialidade, LocalTime inicio, LocalTime fim) {
        super(nome, cpf, telefone, email, endereco);
        this.especialidade = especialidade;
        this.status = StatusDentista.DISPONIVEL;
        this.horarioTrabalhoInicio = inicio;
        this.horarioTrabalhoFim = fim;
        //diasDeFolga.add(DayOfWeek.SATURDAY); Fiz métodos específicos pra gerenciar isso
        diasDeFolga.add(DayOfWeek.SUNDAY); //Vou manter só domingo no automatico pq ngm é de ferro
    }

    //Getters e Setters
    public String getEspecialidade() {
        return this.especialidade;
    }

    public List<Agendamento> getAgenda() {
        return this.agenda;
    }

    public List<Agendamento> getHistorico() {
        return this.historicoConsultas;
    }

    public LocalTime getHorarioTrabalhoInicio() {
        return this.horarioTrabalhoInicio;
    }

    public LocalTime getHorarioTrabalhoFim() {
        return this.horarioTrabalhoFim;
    }

    public List<DayOfWeek> getDiasDeFolga() {
        return this.diasDeFolga;
    }

    public StatusDentista getStatus() {
        return this.status;
    }
    
    //Métodos
    public void adicionarAgendamento(Agendamento agendamento) {
        this.agenda.add(agendamento);
    }

    public void removerAgendamento(Agendamento agendamento) {
        this.agenda.remove(agendamento);
    }

    public void adicionarNoHistorico(Agendamento agendamento) {
        this.historicoConsultas.add(agendamento);
    }

    public void AdicionarDiaDeFolga(DayOfWeek leDia) {
        if(!this.diasDeFolga.contains(leDia))
            this.diasDeFolga.add(leDia);
        else
            System.err.println("Dia ja esta registrado como folga.");
    }

    public void RemoverDiaDeFolga(DayOfWeek leDia) {
        if(this.diasDeFolga.contains(leDia))
            this.diasDeFolga.remove(leDia);
        else
            System.err.println("Dia nao esta registrado como folga.");
    }

    public void registrarAusencia(LocalDate data, String motivo) {
        periodosAusencia.put(data, motivo);
    }

    public boolean estaAusente(LocalDate data) {
        return periodosAusencia.containsKey(data);
    }
}