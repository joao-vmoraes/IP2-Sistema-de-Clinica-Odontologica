package clinica.view.UIController;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import clinica.model.*;

public class AgendamentoController implements Initializable {

    @FXML
    private ComboBox<Paciente> comboPaciente;

    @FXML
    private ComboBox<Dentista> comboDentista;

    @FXML
    private DatePicker datePickerData;

    @FXML
    private ComboBox<String> comboHorario;

    @FXML
    private TextArea txtObservacao;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
    ObservableList<Paciente> pacientes = FXCollections.observableArrayList();
    comboPaciente.setItems(pacientes);

    ObservableList<Dentista> dentistas = FXCollections.observableArrayList();
    comboDentista.setItems(dentistas);

    ObservableList<String> horarios = FXCollections.observableArrayList();
    comboHorario.setItems(horarios);
}

    @FXML
    void handleSalvar(ActionEvent event) {
        if (camposInvalidos()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Obrigatórios", "Por favor, preencha Paciente, Dentista, Data e Hora.");
            return;
        }

        Paciente paciente = comboPaciente.getValue(); 
        Dentista dentista = comboDentista.getValue();
        LocalDate data = datePickerData.getValue();
        String hora = comboHorario.getValue();
        String obs = txtObservacao.getText();

        System.out.println("Salvando Agendamento: " + paciente + " com " + dentista + " em " + data + " às " + hora);
        
        mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Agendamento realizado com sucesso!");
        limparCampos();
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        fecharJanela();
    }

    private boolean camposInvalidos() {
        return comboPaciente.getValue() == null || 
               comboDentista.getValue() == null || 
               datePickerData.getValue() == null || 
               comboHorario.getValue() == null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    private void limparCampos() {
        comboPaciente.getSelectionModel().clearSelection();
        comboDentista.getSelectionModel().clearSelection();
        datePickerData.setValue(null);
        comboHorario.getSelectionModel().clearSelection();
        txtObservacao.clear();
    }

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
}