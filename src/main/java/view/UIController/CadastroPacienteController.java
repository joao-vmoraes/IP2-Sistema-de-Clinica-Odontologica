package view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CadastroPacienteController {

    // 1. Injeção de Componentes da Tela (UI Injection)
    // O @FXML conecta a variável ao componente fx:id correspondente no FXML.
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;

    // 2. Dependência do Serviço de Negócio (Business Service Dependency)
    // O Controller da UI NÃO acessa o Repositório, ele acessa o Cadastrador.
    private Cadastrador cadastrador;

    // 3. Setter para Injeção de Dependência
    // Este método será chamado pela classe principal (main.java.AppFX) para nos entregar o Cadastrador.
    public void setCadastrador(Cadastrador c) {
        this.cadastrador = c;
    }

    // 4. Método de Ação (Event Handler)
    // Conectado ao onAction="#acaoSalvar" do botão CADASTRAR no FXML.
    @FXML
    private void acaoSalvar() {
        if (cadastrador == null) {
            System.err.println("Erro: Serviço Cadastrador não foi injetado.");
            return;
        }

        // Coleta os dados da interface
        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String endereco = txtEndereco.getText();

        // Cria o objeto Model
        Paciente novoPaciente = new Paciente(nome, cpf, telefone, email, endereco);

        // Delega a responsabilidade de salvar para a camada de serviço (Cadastrador)
        cadastrador.cadastrarPaciente(novoPaciente);

        System.out.println("GUI: Paciente " + nome + " enviado para o serviço de cadastro.");

        // Opcional: Limpar campos após o cadastro
        txtNome.clear();
        txtCpf.clear();
        // ... limpar outros campos
    }
}