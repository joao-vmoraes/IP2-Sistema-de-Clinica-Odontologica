package clinica.repository;

import clinica.model.Dentista;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DentistaRepositorio {
    private List<Dentista> dentistas = new ArrayList<>();

    public void salvar(Dentista dentista) {
        if (!dentistas.contains(dentista)) {
            dentistas.add(dentista);
        }
    }

    public List<Dentista> listarTodos() {
        return new ArrayList<>(dentistas);
    }

    public Dentista buscarPorCpf(String cpf) {
        for (Dentista p : dentistas) {
            if (p.getCpf().equals(cpf)) return p;
        }
        return null;
    }

    // NOVO: Busca por parte do CPF (para filtros)
    public List<Dentista> buscarPorParteCpf(String termo) {
        String termoLimpo = termo.replaceAll("[^0-9]", "");
        return dentistas.stream()
                .filter(d -> d.getCpf().replaceAll("[^0-9]", "").contains(termoLimpo))
                .collect(Collectors.toList());
    }

    // NOVO: Busca por disponibilidade de horário
    // Verifica se o dentista trabalha naquele horário (não verifica agendamentos aqui, apenas expediente)
    public List<Dentista> buscarPorDisponibilidadeHorario(LocalTime horario) {
        return dentistas.stream()
                .filter(d -> {
                    // Se o horário buscado está dentro do expediente
                    // (horario >= inicio E horario < fim)
                    return !horario.isBefore(d.getHorarioTrabalhoInicio()) &&
                            horario.isBefore(d.getHorarioTrabalhoFim());
                })
                .collect(Collectors.toList());
    }

    public void atualizar(Dentista dentista) {
        Dentista d = buscarPorCpf(dentista.getCpf());
        if (d != null) {
            int index = dentistas.indexOf(d);
            dentistas.set(index, dentista);
        }
    }

    public void deletar(Dentista dentista) {
        dentistas.remove(dentista);
    }
}