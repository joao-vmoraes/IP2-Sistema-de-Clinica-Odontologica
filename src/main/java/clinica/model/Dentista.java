package clinica.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clinica.enums.DiasSemana;
import clinica.enums.StatusDentista;

public class Dentista extends Pessoa {
    private String especialidade;
    private StatusDentista status;
    private LocalTime horarioTrabalhoInicio;
    private LocalTime horarioTrabalhoFim;
    private List<DiasSemana> diasDeFolga = new ArrayList<>();
    private Map<LocalDate, String> periodosAusencia = new HashMap<>();

    //CONSTRUTOR
    public Dentista(String nome, String cpf, String telefone, String email, String endereco, String especialidade, LocalTime inicio, LocalTime fim) {
        super(nome, cpf, telefone, email, endereco);
        this.especialidade = especialidade;
        this.status = StatusDentista.DISPONIVEL;
        this.horarioTrabalhoInicio = inicio;
        this.horarioTrabalhoFim = fim;
    }

    public Dentista(String nome, String cpf, String telefone, String email, String endereco, String especialidade, LocalTime inicio, LocalTime fim, DiasSemana folga) {
        super(nome, cpf, telefone, email, endereco);
        this.especialidade = especialidade;
        this.status = StatusDentista.DISPONIVEL;
        this.horarioTrabalhoInicio = inicio;
        this.horarioTrabalhoFim = fim;
        this.diasDeFolga.add(folga);
    }

    //Getters e Setters
    public String getEspecialidade() {
        return this.especialidade;
    }


    public LocalTime getHorarioTrabalhoInicio() {
        return this.horarioTrabalhoInicio;
    }

    public LocalTime getHorarioTrabalhoFim() {
        return this.horarioTrabalhoFim;
    }

    public List<DiasSemana> getDiasDeFolga() {
        return this.diasDeFolga;
    }

    public StatusDentista getStatus() {
        return this.status;
    }

    //Métodos



    public void AdicionarDiaDeFolga(DiasSemana leDia) {
        if(!this.diasDeFolga.contains(leDia))
            this.diasDeFolga.add(leDia);
        else
            System.err.println("Dia ja esta registrado como folga.");
    }

    public void RemoverDiaDeFolga(DiasSemana leDia) {
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