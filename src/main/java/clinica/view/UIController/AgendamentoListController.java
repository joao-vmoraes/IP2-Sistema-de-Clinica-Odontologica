package clinica.view.UIController;

import clinica.controller.ClinicaManager;
import clinica.enums.StatusAgendamento;
import clinica.model.Agendamento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AgendamentoListController {

    @FXML private TextField txtPesquisaCpf;
    @FXML private TableView<Agendamento> tableViewAgendamentos;
    @FXML private TableColumn<Agendamento, String> colDataHora;
    @FXML private TableColumn<Agendamento, String> colPaciente;
    @FXML private TableColumn<Agendamento, String> colDentista;
    @FXML private TableColumn<Agendamento, String> colProcedimento;
    @FXML private TableColumn<Agendamento, String> colStatus;
    @FXML private TableColumn<Agendamento, String> colFinanceiro;
    @FXML private TableColumn<Agendamento, Void> colAcoes;

    private ClinicaManager clinicaManager;
    private MainController mainController;

    public void setDependencies(ClinicaManager manager, MainController main) {
        this.clinicaManager = manager;
        this.mainController = main;
        atualizarLista();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colDataHora.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataHora().format(dtf)));

        colPaciente.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPaciente().getNome()));

        colDentista.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDentista().getNome()));

        colProcedimento.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProcedimento().getNome()));

        colStatus.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus().toString()));

        colFinanceiro.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().isPago() ? "PAGO" : "PENDENTE"));

        adicionarBotoesAcao();
    }

    private void adicionarBotoesAcao() {
        Callback<TableColumn<Agendamento, Void>, TableCell<Agendamento, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnAtender = new Button("Atender");
            private final Button btnCancelar = new Button("Cancelar");
            private final HBox pane = new HBox(5, btnAtender, btnCancelar);

            {
                btnAtender.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                btnAtender.setOnAction(event -> {
                    Agendamento ag = getTableView().getItems().get(getIndex());
                    if(ag.getStatus() != StatusAgendamento.CANCELADO) {
                        mainController.loadAtendimento(ag);
                    } else {
                        mostrarAlerta("Aviso", "Não é possível atender agendamento cancelado.");
                    }
                });

                btnCancelar.setOnAction(event -> {
                    Agendamento ag = getTableView().getItems().get(getIndex());
                    confirmarCancelamento(ag);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void confirmarCancelamento(Agendamento agendamento) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Cancelar Agendamento");
        alert.setContentText("Tem certeza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            clinicaManager.cancelarAgendamento(agendamento);
            tableViewAgendamentos.refresh();
        }
    }

    @FXML
    public void filtrarPorCpf() {
        String termo = txtPesquisaCpf.getText();
        if (termo == null || termo.isEmpty()) {
            atualizarLista(); return;
        }
        if (clinicaManager != null) {
            List<Agendamento> filtrados = clinicaManager.buscarAgendamentosPorCpfPaciente(termo);
            if (filtrados.isEmpty()) mostrarAlerta("Pesquisa", "Nenhum encontrado.");
            tableViewAgendamentos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    @FXML
    public void atualizarLista() {
        if (clinicaManager != null) {
            txtPesquisaCpf.clear();
            tableViewAgendamentos.setItems(
                    FXCollections.observableArrayList(clinicaManager.listarTodosAgendamentos())
            );
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}