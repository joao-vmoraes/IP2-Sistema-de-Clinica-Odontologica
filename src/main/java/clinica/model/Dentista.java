package clinica.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clinica.enums.StatusDentista;

public class Dentista extends Pessoa {
    private String especialidade;
    private StatusDentista status;
<<<<<<< Updated upstream
    private LocalTime horarioTrabalhoInicio; 
    private LocalTime horarioTrabalhoFim;
    private List<DayOfWeek> diasDeFolga = new ArrayList<>();
=======
    
    // MUDANÇA PRINCIPAL: Substituímos inicio/fim fixos por um mapa de disponibilidade
    // Exemplo de estrutura: 
    // MONDAY -> [08:00, 09:00, 14:00]
    // TUESDAY -> [08:00, 10:00]
    private Map<DayOfWeek, List<LocalTime>> gradeDisponibilidade = new HashMap<>();

>>>>>>> Stashed changes
    private Map<LocalDate, String> periodosAusencia = new HashMap<>();

    // CONSTRUTOR ATUALIZADO (Removemos horários do construtor para simplificar)
    public Dentista(String nome, String cpf, String telefone, String email, String endereco, String especialidade) {
        super(nome, cpf, telefone, email, endereco);
        this.especialidade = especialidade;
        this.status = StatusDentista.DISPONIVEL;
<<<<<<< Updated upstream
        this.horarioTrabalhoInicio = inicio;
        this.horarioTrabalhoFim = fim;
        //diasDeFolga.add(DayOfWeek.SATURDAY); Fiz métodos específicos pra gerenciar isso
        diasDeFolga.add(DayOfWeek.SUNDAY); //Vou manter só domingo no automatico pq ngm é de ferro
=======
    }

    // --- MÉTODOS DE LÓGICA DE HORÁRIO ---

    /**
     * Recebe a lista de strings cruas do Controller (Ex: "Seg;08:00")
     * e converte para a estrutura de dados oficial do sistema.
     */
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
>>>>>>> Stashed changes
    }

    /**
     * Verifica se o dentista atende em um dia e hora específicos
     */
    public boolean atendeNesteHorario(DayOfWeek dia, LocalTime hora) {
        if (!gradeDisponibilidade.containsKey(dia)) {
            return false;
        }
        return gradeDisponibilidade.get(dia).contains(hora);
    }

    // Auxiliar para converter as Strings da tela para Enum do Java
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

    // --- GETTERS E SETTERS ---

    public String getEspecialidade() {
        return this.especialidade;
    }

<<<<<<< Updated upstream

    public LocalTime getHorarioTrabalhoInicio() {
        return this.horarioTrabalhoInicio;
    }

    public LocalTime getHorarioTrabalhoFim() {
        return this.horarioTrabalhoFim;
    }

    public List<DayOfWeek> getDiasDeFolga() {
        return this.diasDeFolga;
=======
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
>>>>>>> Stashed changes
    }

    public StatusDentista getStatus() {
        return this.status;
    }
<<<<<<< Updated upstream
    
    //Métodos



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
=======

    public void setStatus(StatusDentista status) {
        this.status = status;
    }

    public Map<DayOfWeek, List<LocalTime>> getGradeDisponibilidade() {
        return gradeDisponibilidade;
>>>>>>> Stashed changes
    }

    // --- MÉTODOS DE AUSÊNCIA (Mantidos) ---

    public void registrarAusencia(LocalDate data, String motivo) {
        periodosAusencia.put(data, motivo);
    }

    public boolean estaAusente(LocalDate data) {
        return periodosAusencia.containsKey(data);
    }
}