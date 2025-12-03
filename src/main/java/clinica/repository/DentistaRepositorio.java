package clinica.repository;

import java.util.ArrayList;
import java.util.List;

import clinica.model.Dentista;

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

<<<<<<< Updated upstream
=======
    // Busca por parte do CPF (para filtros)
    public List<Dentista> buscarPorParteCpf(String termo) {
        String termoLimpo = termo.replaceAll("[^0-9]", "");
        return dentistas.stream()
                .filter(d -> d.getCpf().replaceAll("[^0-9]", "").contains(termoLimpo))
                .collect(Collectors.toList());
    }

    // --- MUDANÇA AQUI: Nova Lógica de Busca por Horário ---
    // Verifica se o dentista trabalha naquele horário em ALGUM dia da semana.
    // Como a estrutura agora é um Map<Dia, Lista<Horas>>, precisamos varrer os valores.
    public List<Dentista> buscarPorDisponibilidadeHorario(LocalTime horario) {
        return dentistas.stream()
                .filter(d -> {
                    // d.getGradeDisponibilidade().values() retorna uma coleção de Listas de Horários
                    // Ex: [[08:00, 09:00], [14:00, 15:00]]
                    // Usamos o stream para ver se 'anyMatch' (alguma dessas listas) contém o horário buscado.
                    return d.getGradeDisponibilidade().values().stream()
                            .anyMatch(listaHorariosDoDia -> listaHorariosDoDia.contains(horario));
                })
                .collect(Collectors.toList());
    }

>>>>>>> Stashed changes
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
