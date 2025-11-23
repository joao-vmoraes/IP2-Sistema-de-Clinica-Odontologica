package clinica.repository;

import java.util.ArrayList;
import java.util.List;

import clinica.model.Agendamento;

public class AgendamentoRepositorio {
    private List<Agendamento> agendamentos = new ArrayList<>();

    public void salvar(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    public List<Agendamento> listarTodos() {
        return new ArrayList<>(agendamentos);
    }
}