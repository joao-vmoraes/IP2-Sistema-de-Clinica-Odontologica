package clinica.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clinica.model.Procedimento;

public class ProcedimentoRepositorio {
    private List<Procedimento> procedimentos = new ArrayList<>();

    public void salvar(Procedimento procedimento) {
        Procedimento existente = procurarProcedimento(procedimento.getNome());
        if(existente == null)
        {
            procedimentos.add(procedimento);
        }
        else if(!existente.taDisponivel())
        {
            existente.setDisponibilidade(true);
        }
    }

    public List<Procedimento> listarProcedimentos() {
        List<Procedimento> procedimentosFiltrados = procedimentos.stream().filter(a -> a.taDisponivel()).collect(Collectors.toList());
        return procedimentosFiltrados;
    }

    public Procedimento procurarProcedimento(String nomeProcedimento) {
        for (Procedimento procedimento : procedimentos) {
            if (procedimento.getNome().equals(nomeProcedimento)) {
                return procedimento;
            }
        }
        return null;
    }

    public List<Procedimento> listarTodos() {
        List<Procedimento> procedimentosFiltrados = procedimentos.stream().filter(a -> a.taDisponivel()).collect(Collectors.toList());
        return new ArrayList<>(procedimentosFiltrados);
    }

    public void atualizarProcedimento(Procedimento procedimento) {
        Procedimento proc = procurarProcedimento(procedimento.getNome());
        if (proc != null) {
            int index = procedimentos.indexOf(proc);
            procedimentos.set(index, procedimento);
        }
    }

    public void deletarProcedimento(Procedimento procedimento) {
        procedimento.setDisponibilidade(false);
    }
}