package clinica.view.UIController;

import clinica.enums.StatusAgendamento;
import clinica.model.Agendamento;
import clinica.repository.AgendamentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
// IMPORTS CORRIGIDOS
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private AgendamentoRepositorio agendamentoRepo;
    private MainController mainController; // Precisa do MainController para navegar para a tela de Atendimento

    public void setDependencies(AgendamentoRepositorio repo, MainController main) {
        this.agendamentoRepo = repo;
        this.mainController = main;
        atualizarLista();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colDataHora.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataHora().format(formatter)));

        colPaciente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPaciente().getNome()));

        colDentista.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDentista().getNome()));

        colProcedimento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProcedimento() != null ?
                        cellData.getValue().getProcedimento().getNome() : "-"));

        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colFinanceiro.setCellValueFactory(cellData -> {
            boolean pago = cellData.getValue().isPago();
            return new SimpleStringProperty(pago ? "PAGO " : "PENDENTE ");
        });

        adicionarBotoesAcao();
    }

    private void adicionarBotoesAcao() {
        Callback<TableColumn<Agendamento, Void>, TableCell<Agendamento, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Agendamento, Void> call(final TableColumn<Agendamento, Void> param) {
                return new TableCell<>() {
                    private final Button btnCancelar = new Button("Cancelar");
                    private final Button btnAtender = new Button("Atender");
                    private final HBox pane = new HBox(5, btnAtender, btnCancelar); // HBox para alinhar botões

                    {
                        btnCancelar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-size: 10px;");
                        btnCancelar.setOnAction(event -> {
                            Agendamento agendamento = getTableView().getItems().get(getIndex());
                            cancelarAgendamento(agendamento);
                        });

                        btnAtender.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 10px;");
                        btnAtender.setOnAction(event -> {
                            Agendamento agendamento = getTableView().getItems().get(getIndex());
                            if (mainController != null) {
                                mainController.loadAtendimento(agendamento);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Agendamento agendamento = getTableView().getItems().get(getIndex());

                            // Lógica de visibilidade dos botões
                            if (agendamento.getStatus() == StatusAgendamento.PLANEJADO) {
                                setGraphic(pane); // Mostra os dois
                            } else {
                                setGraphic(null); // Já foi atendido ou cancelado
                            }
                        }
                    }
                };
            }
        };
        colAcoes.setCellFactory(cellFactory);
    }

    private void cancelarAgendamento(Agendamento agendamento) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Cancelar Agendamento");
        alert.setContentText("Tem certeza?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            agendamento.setStatus(StatusAgendamento.CANCELADO);
            tableViewAgendamentos.refresh();
        }
    }

    @FXML
    public void filtrarPorCpf() {
        String termo = txtPesquisaCpf.getText();
        if (termo == null || termo.isEmpty()) {
            atualizarLista(); return;
        }
        if (agendamentoRepo != null) {
            List<Agendamento> filtrados = agendamentoRepo.buscarPorCpfPaciente(termo);
            if (filtrados.isEmpty()) mostrarAlerta("Pesquisa", "Nenhum encontrado.");
            tableViewAgendamentos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    @FXML
    public void atualizarLista() {
        if (agendamentoRepo != null) {
            txtPesquisaCpf.clear();
            tableViewAgendamentos.setItems(
                    FXCollections.observableArrayList(agendamentoRepo.listarTodos())
            );
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}