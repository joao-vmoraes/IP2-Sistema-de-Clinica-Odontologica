package clinica.repository;

import clinica.model.Agendamento;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AgendamentoRepositorio {
    private List<Agendamento> agendamentos = new ArrayList<>();

    public void salvar(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos);
    }

    // NOVO MÉTODO: Filtrar agendamentos por CPF do paciente
    public List<Agendamento> buscarPorCpfPaciente(String cpf) {
        // Remove pontos e traços para facilitar a busca
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");

        return agendamentos.stream()
                .filter(a -> {
                    String cpfPaciente = a.getPaciente().getCpf().replaceAll("[^0-9]", "");
                    return cpfPaciente.contains(cpfLimpo);
                })
                .collect(Collectors.toList());
    }
}