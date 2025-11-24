package clinica.repository;

import clinica.model.Pagamento;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepositorio {
    private List<Pagamento> pagamentos = new ArrayList<>();

    public void salvar(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public List<Pagamento> listarTodos() {
        return new ArrayList<>(pagamentos);
    }
}