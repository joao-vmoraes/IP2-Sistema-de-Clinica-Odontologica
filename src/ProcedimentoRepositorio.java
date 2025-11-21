import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoRepositorio {
    private List<Procedimento> procedimentos = new ArrayList<>();

    public void salvar(Procedimento procedimento) {
        procedimentos.add(procedimento);
    }

    public List<Procedimento> listarProcedimentos() {
        return procedimentos;
    }

    public Procedimento procurarProcedimento(String nomeProcedimento) {
        for (Procedimento procedimento : procedimentos) {
            if (procedimento.getNome().equals(nomeProcedimento)) {
                return procedimento;
            }
        }
        return null;
    }

    public void atualizarProcedimento(Procedimento procedimento) {
        Procedimento proc = procurarProcedimento(procedimento.getNome());
        if (proc != null) {
            int index = procedimentos.indexOf(proc);
            procedimentos.set(index, procedimento);
        }
    }

    public void deletarProcedimento(Procedimento procedimento) {
        procedimentos.remove(procedimento);
    }
}
