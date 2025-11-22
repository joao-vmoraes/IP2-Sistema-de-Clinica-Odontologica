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
