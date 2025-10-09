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
        diasDeFolga.add(DayOfWeek.SATURDAY); 
        diasDeFolga.add(DayOfWeek.SUNDAY);
    }

    //MÉTODOS
    public String getEspecialidade() {
        return especialidade;
    }

    public List<Agendamento> getAgenda() {
        return agenda;
    }

    public void adicionarAgendamento(Agendamento agendamento) {
        this.agenda.add(agendamento);
    }

    public LocalTime getHorarioTrabalhoInicio() {
        return horarioTrabalhoInicio;
    }

    public LocalTime getHorarioTrabalhoFim() {
        return horarioTrabalhoFim;
    }

    public List<DayOfWeek> getDiasDeFolga() {
        return diasDeFolga;
    }
    
    public void registrarAusencia(LocalDate data, String motivo) {
        periodosAusencia.put(data, motivo);
    }

    public boolean estaAusente(LocalDate data) { 
        return periodosAusencia.containsKey(data);
    }
}