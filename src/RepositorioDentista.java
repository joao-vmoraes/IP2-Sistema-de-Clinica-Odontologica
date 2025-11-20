import java.time.LocalTime;
import java.util.ArrayList;

public class RepositorioDentista {
    public static RepositorioDentista instance;

    private ArrayList<Dentista> dentistas = new ArrayList<>();
    
    public static RepositorioDentista getInstance() {
        if (instance == null)
        {
            instance = new RepositorioDentista();
        }
        return instance;
    }

    public RepositorioDentista()
    {
        dentistas = new ArrayList<>();
    }

    public void cadastrar(Dentista d)
    {
        if(d == null)
        {
            System.err.println("Dentista nulo.");
            return;
        }

        if(dentistas.contains(d))
        {
            System.err.println("Dentista ja existe.");
            return;
        }

        dentistas.add(d);
        System.err.println("Dentista cadastrado com sucesso.");
    }

    public void cadastrar(String nome, String cpf, String telefone, String email, String endereco, String especialidade, LocalTime inicio, LocalTime fim)
    {
        Dentista d = new Dentista(nome, cpf, telefone, email, endereco, especialidade, inicio, fim);
        dentistas.add(d);
        System.err.println("Dentista cadastrado com sucesso.");
    }

    //Ao invés de remover o dentista do repositório, acho melhor ter uma função nele que o seta como "aposentado" ou algo assim
    /*public void remover(Dentista d)
    {
        if(d == null)
        {
            System.err.println("Dentista nulo.");
            return;
        }

        if(!dentistas.contains(d))
        {
            System.err.println("Dentista nao cadastrado.");
            return;
        }

        dentistas.remove(d);
        System.err.println("Dentista removido com sucesso.");
    }*/

    public Dentista getDentistaPorNome(String nome)
    {
        for(int i = 0; i < dentistas.size(); i++)
        {
            if(dentistas.get(i).nome.equals(nome))
            {
                return dentistas.get(i);
            }
        }
        System.err.println("Nome nao encontrado.");
        return null;
    }

    public Dentista getDentistaPorCpf(String cpf)
    {
        for(int i = 0; i < dentistas.size(); i++)
        {
            if(dentistas.get(i).cpf.equals(cpf))
            {
                return dentistas.get(i);
            }
        }
        System.err.println("Cpf nao encontrado.");
        return null;
    }

    public Dentista getDentistaPorEmail(String email)
    {
        for(int i = 0; i < dentistas.size(); i++)
        {
            if(dentistas.get(i).email.equals(email))
            {
                return dentistas.get(i);
            }
        }
        System.err.println("Email nao encontrado.");
        return null;
    }

    public ArrayList<Dentista> getListaCompleta()
    {
        return dentistas;
    }
}
