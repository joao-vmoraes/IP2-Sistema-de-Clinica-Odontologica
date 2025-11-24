package clinica.view.UIController;

import clinica.model.Paciente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class PagamentoController {

    @FXML
    private ComboBox<Paciente> comboPaciente;

    @FXML
    private TextField txtValor;

    @FXML
    private ComboBox<String> comboMetodo;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private TextArea txtObs;

    @FXML
    public void initialize() {
        carregarDados();
    }

    private void carregarDados() {
        // 1. Carregar Pacientes (Reaproveitando a classe Paciente que criamos antes)
        ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
        comboPaciente.setItems(pacientes);

        // 2. Carregar Formas de Pagamento
        ObservableList<String> metodos = FXCollections.observableArrayList(
            "Dinheiro", "Cartão de Crédito", "Cartão de Débito", "PIX", "Convênio"
        );
        comboMetodo.setItems(metodos);

        // 3. Definir data padrão como hoje
        datePickerData.setValue(LocalDate.now());
    }

    @FXML
    private void handleRegistrar() {
        // Validação básica
        if (comboPaciente.getValue() == null || txtValor.getText().isEmpty() || comboMetodo.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Por favor, preencha o paciente, valor e método de pagamento.");
            return;
        }

        try {
            // Tenta converter o texto para número (Double)
            // Substitui vírgula por ponto para evitar erros se o usuário digitar "150,00"
            String valorTexto = txtValor.getText().replace(",", ".");
            double valor = Double.parseDouble(valorTexto);

            // Captura os outros dados
            Paciente paciente = comboPaciente.getValue();
            String metodo = comboMetodo.getValue();
            LocalDate data = datePickerData.getValue();
            String obs = txtObs.getText();

            // SUCESSO - Aqui você chamaria seu Service/Banco de Dados
            System.out.println("PAGAMENTO REGISTRADO:");
            System.out.println("Pagador: " + paciente.getNome());
            System.out.println("Valor: R$ " + valor);
            System.out.println("Método: " + metodo);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Pagamento de R$ " + valor + " registrado com sucesso!");
            
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Valor Inválido", "O campo 'Valor' deve conter apenas números. Ex: 150.00");
        }
    }

    @FXML
    private void handleCancelar() {
        // Limpa ou fecha a janela
        comboPaciente.getScene().getWindow().hide();
    }

    private void limparCampos() {
        comboPaciente.getSelectionModel().clearSelection();
        txtValor.clear();
        comboMetodo.getSelectionModel().clearSelection();
        txtObs.clear();
        datePickerData.setValue(LocalDate.now());
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String msg) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}