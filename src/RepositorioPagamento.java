import java.time.LocalDateTime;
import java.util.ArrayList;

public class RepositorioPagamento {
    public static RepositorioPagamento instance;

    private ArrayList<Pagamento> pagamentos = new ArrayList<>();
    
    public static RepositorioPagamento getInstance() {
        if (instance == null)
        {
            instance = new RepositorioPagamento();
        }
        return instance;
    }

    public RepositorioPagamento()
    {
        pagamentos = new ArrayList<>();
    }

    public void cadastrar(Pagamento a)
    {
        if(a == null)
        {
            System.err.println("Pagamento nulo.");
            return;
        }

        if(pagamentos.contains(a))
        {
            System.err.println("Pagamento ja existe.");
            return;
        }

        pagamentos.add(a);
        System.err.println("Pagamento cadastrado com sucesso.");
    }

    public ArrayList<Pagamento> getPagamentos(double valor)
    {
        ArrayList<Pagamento> pagamentosEncontrados = new ArrayList<>();
        for(int i = 0; i < pagamentos.size(); i++)
        {
            if(pagamentos.get(i).getValor() == valor)
            {
                pagamentosEncontrados.add(pagamentos.get(i));
            }
        }

        if(pagamentosEncontrados.isEmpty())
            System.err.println("Nenhum pagamento encontrado.");
        
        return pagamentosEncontrados;
    }

    public ArrayList<Pagamento> getPagamentos(double valor, LocalDateTime data)
    {
        ArrayList<Pagamento> pagamentosEncontrados = new ArrayList<>();
        for(int i = 0; i < pagamentos.size(); i++)
        {
            if(pagamentos.get(i).getValor() == valor && pagamentos.get(i).getDataPagamento().equals(data))
            {
                pagamentosEncontrados.add(pagamentos.get(i));
            }
        }

        if(pagamentosEncontrados.isEmpty())
            System.err.println("Nenhum pagamento encontrado.");
        
        return pagamentosEncontrados;
    }

    public ArrayList<Pagamento> getListaCompleta()
    {
        return pagamentos;
    }
}
