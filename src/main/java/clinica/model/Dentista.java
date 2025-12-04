package clinica.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Map<DayOfWeek, List<LocalTime>> gradeDisponibilidade = new HashMap<>();
    private Map<LocalDate, String> periodosAusencia = new HashMap<>();

    //CONSTRUTOR
    public Dentista(String nome, String cpf, String telefone, String email, String endereco, String especialidade) {
        super(nome, cpf, telefone, email, endereco);
        this.especialidade = especialidade;
        this.status = StatusDentista.DISPONIVEL;
    }

    public void configurarHorarios(List<String> listaHorariosTexto) {
        // Limpa horários antigos
        this.gradeDisponibilidade.clear();

        for (String item : listaHorariosTexto) {
            // item vem como "Seg;08:00" ou "Qui;14:00"
            String[] partes = item.split(";");
            if (partes.length < 2) continue;

            String diaTexto = partes[0];
            String horaTexto = partes[1];

            DayOfWeek diaSemana = converterDia(diaTexto);
            LocalTime horario = LocalTime.parse(horaTexto);

            // Adiciona na lista correta dentro do Mapa
            this.gradeDisponibilidade.putIfAbsent(diaSemana, new ArrayList<>());
            this.gradeDisponibilidade.get(diaSemana).add(horario);
        }
    }

    public boolean atendeNesteHorario(DiasSemana dia, LocalTime hora) {
        if (!gradeDisponibilidade.containsKey(dia)) {
            return false;
        }
        return gradeDisponibilidade.get(dia).contains(hora);
    }

    private DayOfWeek converterDia(String diaCurto) {
        switch (diaCurto) {
            case "Seg": return DayOfWeek.MONDAY;
            case "Ter": return DayOfWeek.TUESDAY;
            case "Qua": return DayOfWeek.WEDNESDAY;
            case "Qui": return DayOfWeek.THURSDAY;
            case "Sex": return DayOfWeek.FRIDAY;
            case "Sáb": return DayOfWeek.SATURDAY;
            case "Dom": return DayOfWeek.SUNDAY;
            default: return DayOfWeek.MONDAY; // Fallback
        }
    }

    //Getters e Setters
    public String getEspecialidade() {
        return this.especialidade;
    }


    public LocalTime getHorarioTrabalhoInicio(DayOfWeek leDia) {
        
        List<LocalTime> hora = this.gradeDisponibilidade.get(leDia);
        return hora == null ? null : hora.getFirst();
    }

    public LocalTime getHorarioTrabalhoFim(DayOfWeek leDia) {
        List<LocalTime> hora = this.gradeDisponibilidade.get(leDia);
        return hora == null ? null : hora.getLast();
    }

    public List<DayOfWeek> getDiasDeFolga() {
        List<DayOfWeek> dias = new ArrayList();
        dias.add(DayOfWeek.MONDAY);
        dias.add(DayOfWeek.TUESDAY);
        dias.add(DayOfWeek.WEDNESDAY);
        dias.add(DayOfWeek.THURSDAY);
        dias.add(DayOfWeek.FRIDAY);
        dias.add(DayOfWeek.SATURDAY);
        dias.add(DayOfWeek.SUNDAY);

        List<DayOfWeek> diasFaltano = new ArrayList();
        for(int i = 0; i < 6; i++)
        {
            if(!this.gradeDisponibilidade.containsKey(dias.get(i)))
            {
                diasFaltano.add(dias.get(i));
            }
        }
        return diasFaltano;
    }

    public StatusDentista getStatus() {
        return this.status;
    }

    //Métodos



    /*public void AdicionarDiaDeFolga(DayOfWeek leDia) {
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
    }*/

    public void setStatus(StatusDentista status) {
        this.status = status;
    }

    public Map<DayOfWeek, List<LocalTime>> getGradeDisponibilidade() {
        return this.gradeDisponibilidade;
    }

    public void registrarAusencia(LocalDate data, String motivo) {
        periodosAusencia.put(data, motivo);
    }

    public boolean estaAusente(LocalDate data) {
        return periodosAusencia.containsKey(data);
    }

    public boolean estaDisponivel(LocalTime hora, DayOfWeek dia) {
        if(!this.gradeDisponibilidade.containsKey(dia))
            return false;

        List<LocalTime> laHora = this.gradeDisponibilidade.get(dia);
        if(!laHora.contains(hora))
            return false;

        return true;
    }
}