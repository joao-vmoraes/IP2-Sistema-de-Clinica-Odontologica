package clinica.view.UIController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.util.converter.LocalDateTimeStringConverter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class CadastroDentistaController {

    // Elementos visuais (Devem bater com os fx:id do FXML)
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtEspecialidade;
    @FXML private TextField timeInicial;
    @FXML private TextField timeFinal;

    // Dependência (Serviço de Negócio)
    private Cadastrador cadastrador;

    // Método Setter para Injeção de Dependência
    public void setCadastrador(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
    }

    // Ação do Botão Salvar
    @FXML
    private void acaoSalvar() {
        // Validação simples para evitar erro
        if (cadastrador == null) {
            mostrarAlerta("Erro Crítico", "O serviço Cadastrador não foi injetado!");
            return;
        }

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();
        String telefone = txtTelefone.getText();
        String email = txtEmail.getText();
        String endereco = txtEndereco.getText();
        String especialidade = txtEspecialidade.getText();

        String pattern = "HH:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

        LocalTime dataInicial = LocalTime.parse(timeInicial.getText(), formatter);
        LocalTime dataFinal = LocalTime.parse(timeFinal.getText(), formatter);

        // Validação básica de campos vazios
        if (nome.isEmpty() || cpf.isEmpty()) {
            mostrarAlerta("Atenção", "Nome e CPF são obrigatórios.");
            return;
        }

        try {
            // Criação do objeto
            Dentista novoDentista = new Dentista(nome, cpf, telefone, email, endereco, especialidade, dataInicial, dataFinal);

            // Chamada ao serviço
            cadastrador.cadastrarDentista(novoDentista);

            mostrarAlerta("Sucesso", "Dentista " + nome + " cadastrado com sucesso!");
            limparCampos();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtEndereco.clear();
        txtEspecialidade.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}