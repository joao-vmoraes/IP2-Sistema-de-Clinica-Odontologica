import java.util.ArrayList;
import java.util.List;

public class Atendimento {
    protected Paciente paciente;
    protected Dentista dentista;
    protected Procedimento procedimento;
    protected List<Consulta> historicoProcedimentos = new ArrayList<>(); 

    public void AdicionarNoHistorico(Consulta consulta) {
        this.historicoProcedimentos.add(consulta);
    }
    
    public Atendimento() {

    }
}
