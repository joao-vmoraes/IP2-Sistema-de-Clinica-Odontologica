import java.util.ArrayList;

public class RepositorioProcedimentos {
    public static RepositorioProcedimentos instance;

    private ArrayList<Procedimento> procedimentos = new ArrayList<>();
    
    public static RepositorioProcedimentos getInstance() {
        if (instance == null)
        {
            instance = new RepositorioProcedimentos();
        }
        return instance;
    }

    public RepositorioProcedimentos()
    {
        procedimentos = new ArrayList<>();
    }

    public void cadastrar(Procedimento a)
    {
        if(a == null)
        {
            System.err.println("Procedimento nulo.");
            return;
        }

        if(procedimentos.contains(a))
        {
            System.err.println("Procedimento ja existe.");
            return;
        }

        procedimentos.add(a);
        System.err.println("Procedimento cadastrado com sucesso.");
    }

    public void cadastrar(String descricao, double preco, int duracaoEmMinutos)
    {
        Procedimento pro = new Procedimento(descricao, preco, duracaoEmMinutos);
        procedimentos.add(pro);
        System.err.println("Procedimnento cadastrado com sucesso.");
    }

    public Procedimento getProcedimento(String descricao)
    {
        for(int i = 0; i < procedimentos.size(); i++)
        {
            if(procedimentos.get(i).getDescricao().equals(descricao))
            {
                return procedimentos.get(i);
            }
        }
        System.err.println("Procedimento nao encontrado.");
        return null;
    }

    public ArrayList<Procedimento> getListaCompleta()
    {
        return procedimentos;
    }
}
