
import java.util.ArrayList;
import java.util.List;

public class Cadastrador {
    private ClinicaManager manager;
    private List<Paciente> pacientes = new ArrayList<>();
    private List<Dentista> dentistas = new ArrayList<>();
    private List<Procedimento> procedimentos = new ArrayList<>();

    public Cadastrador(ClinicaManager manager) {
        this.manager = manager;
        this.pacientes = manager.getPacientes();
        this.dentistas = manager.getDentistas();
        this.procedimentos = manager.getProcedimentos();
    }

    //Métodos CRUD básicos (REQ01, REQ04)
    public void cadastrarPaciente(Paciente paciente) {
        if(!pacientes.contains(paciente))
            pacientes.add(paciente);
        else
            System.err.println("Paciente ja cadastrado.");
    }

    public void RemoverPaciente(Paciente paciente) {
        if(pacientes.contains(paciente))
            pacientes.remove(paciente);
        else
            System.err.println("Paciente nao encontrado.");
    }

    public void AtualizarPaciente(Paciente pacienteNovo, Paciente pacienteAntigo) {
        if(pacientes.contains(pacienteAntigo))
        {
            pacientes.remove(pacienteAntigo);
            pacientes.add(pacienteNovo);
        }else{
            System.err.println("Paciente nao encontrado.");  
        }
    }

    public void cadastrarDentista(Dentista dentista) {
        if(!dentistas.contains(dentista))
            dentistas.add(dentista);
        else
            System.err.println("Dentista ja cadastrado.");
    }

    public void RemoverDentista(Dentista dentista) {
        if(dentistas.contains(dentista))
            dentistas.remove(dentista);
        else
            System.err.println("Dentista nao encontrado.");
    }

    public void AtualizarDentista(Dentista dentistaNovo, Dentista dentistaAntigo) {
        if(dentistas.contains(dentistaAntigo))
        {
            dentistas.remove(dentistaAntigo);
            dentistas.add(dentistaNovo);
        }else{
            System.err.println("Dentista nao encontrado.");  
        }
    }

    public void adicionarProcedimentoAoCatalogo(Procedimento proc) {
        if(!procedimentos.contains(proc))
            procedimentos.add(proc);
        else
            System.err.println("Procedimento ja existe");
    }

    public void removerProcedimentoDoCatalogo(Procedimento proc) {
        if(procedimentos.contains(proc))
            procedimentos.remove(proc);
        else
            System.err.println("Procedimento nao encontrado");
    }
}
