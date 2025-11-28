package clinica.view.UIController;

import clinica.controller.ClinicaManager;
import clinica.model.Agendamento;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AtendimentoController {

    @FXML private Label lblPaciente;
    @FXML private Label lblProcedimento;
    @FXML private Label lblDentista;
    @FXML private TextArea txtAnotacoes;
    @FXML private Button btnFinalizar;

    private Agendamento agendamentoAtual;
    private ClinicaManager clinicaManager;
    private MainController mainController;

    public void setDependencies(Agendamento agendamento, ClinicaManager manager, MainController main) {
        this.agendamentoAtual = agendamento;
        this.clinicaManager = manager;
        this.mainController = main;

        carregarDados();
    }

    private void carregarDados() {
        if (agendamentoAtual != null) {
            lblPaciente.setText("Paciente: " + agendamentoAtual.getPaciente().getNome());
            lblProcedimento.setText("Procedimento: " + agendamentoAtual.getProcedimento().getNome());
            lblDentista.setText("Dentista: " + agendamentoAtual.getDentista().getNome());
        }
    }

    @FXML
    private void handleFinalizar() {
        if (txtAnotacoes.getText().isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Atenção", "Por favor, escreva as anotações do atendimento.");
            return;
        }

        try {
            clinicaManager.finalizarAtendimento(agendamentoAtual, txtAnotacoes.getText());
            mostrarAlerta(AlertType.INFORMATION, "Sucesso", "Atendimento finalizado com sucesso!");

            if (mainController != null) {
                mainController.loadAgendamentoList();
            }

        } catch (Exception e) {
            mostrarAlerta(AlertType.ERROR, "Erro", "Falha ao finalizar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}