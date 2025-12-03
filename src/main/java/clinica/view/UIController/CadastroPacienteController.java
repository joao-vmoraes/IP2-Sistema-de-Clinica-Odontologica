package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Paciente;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CadastroPacienteController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private Button btnDeletar;
    
    private Cadastrador cadastrador;

    public void setCadastrador(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
    }

    @FXML
    private void acaoSalvar() {
        if (cadastrador == null) {
            mostrarAlerta("Erro Crítico", "O serviço Cadastrador não foi injetado!");
            return;
        }

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String endereco = txtEndereco.getText();

        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Atenção", "Nome e CPF são obrigatórios.");
            return;
        }

        try {
            Paciente novoPaciente = new Paciente(nome, cpf, telefone, email, endereco);

            cadastrador.cadastrar(novoPaciente);

            mostrarAlerta("Sucesso", "Paciente " + nome + " cadastrado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void acaoDeletar() {
        if (cadastrador == null) {
            mostrarAlerta("Erro Crítico", "O serviço Cadastrador não foi injetado!");
            return;
        }
        

    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtEndereco.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}