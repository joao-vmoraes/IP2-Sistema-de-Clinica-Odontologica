import java.util.ArrayList;
import java.util.List;

public class Atendimento {
    protected Paciente paciente;
    protected Dentista dentista;
    protected Procedimento procedimento;
    protected List<Agendamento> historicoProcedimentos = new ArrayList<>(); 

    public void AdicionarNoHistorico(Agendamento agendamento) {
        this.historicoProcedimentos.add(agendamento);
    }
    
    public Atendimento() {

    }
}
