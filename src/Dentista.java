import java.util.ArrayList;
import java.util.List;

public class Dentista {

    private String nome;
    private String cpf;
    private String especialidade;
    private List<Agendamento> agenda = new ArrayList<>();

    //COSTRUTOR
    public Dentista(String nome, String cpf, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    //MÉTODOS

}
