package clinica.repository;

import clinica.model.Atendimento;
import java.util.ArrayList;
import java.util.List;

public class AtendimentoRepositorio {
    private List<Atendimento> atendimentos = new ArrayList<>();

    public void salvar(Atendimento atendimento) {
        atendimentos.add(atendimento);
    }

    public List<Atendimento> listarTodos() {
        return new ArrayList<>(atendimentos);
    }

    // Método útil para ver histórico de um paciente
    public List<Atendimento> buscarPorPaciente(String cpfPaciente) {
        List<Atendimento> historico = new ArrayList<>();
        for (Atendimento a : atendimentos) {
            if (a.getAgendamentoOriginal().getPaciente().getCpf().equals(cpfPaciente)) {
                historico.add(a);
            }
        }
        return historico;
    }
}