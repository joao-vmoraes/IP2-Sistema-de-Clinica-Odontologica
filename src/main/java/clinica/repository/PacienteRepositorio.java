package clinica.repository;

import java.util.ArrayList;
import java.util.List;

import clinica.model.Paciente;

public class PacienteRepositorio {
    private List<Paciente> pacientes =  new ArrayList<>();

    public void salvar(Paciente paciente) {
        if (!pacientes.contains(paciente)) {
            pacientes.add(paciente);
        }
    }

    public List<Paciente> listarTodos() {
        return new ArrayList<>(pacientes);
    }

    public Paciente buscarPorCpf(String cpf) {
        for (Paciente p : pacientes) {
            if (p.getCpf().equals(cpf)) return p;
        }
        return null;
    }

    public void atualizar(Paciente paciente) {
        Paciente p = buscarPorCpf(paciente.getCpf());
        if (p != null) {
            int index = pacientes.indexOf(p);
            pacientes.set(index, paciente);
        }
    }

    public void deletar(Paciente paciente) {
        pacientes.remove(paciente);
    }
}


