package clinica.view.UIController;

import clinica.model.Agendamento;
import clinica.repository.AgendamentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AgendamentoListController {

    @FXML private TextField txtPesquisaCpf; // Campo de busca
    @FXML private TableView<Agendamento> tableViewAgendamentos;
    @FXML private TableColumn<Agendamento, String> colDataHora;
    @FXML private TableColumn<Agendamento, String> colPaciente;
    @FXML private TableColumn<Agendamento, String> colDentista;
    @FXML private TableColumn<Agendamento, String> colProcedimento;
    @FXML private TableColumn<Agendamento, String> colStatus;
    @FXML private TableColumn<Agendamento, String> colFinanceiro; // Nova Coluna

    private AgendamentoRepositorio agendamentoRepo;

    public void setAgendamentoRepositorio(AgendamentoRepositorio repo) {
        this.agendamentoRepo = repo;
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

        // --- Lógica da Coluna Financeiro ---
        colFinanceiro.setCellValueFactory(cellData -> {
            // Verifica o status de pagamento DO AGENDAMENTO (item individual)
            boolean pago = cellData.getValue().isPago();
            return new SimpleStringProperty(pago ? "PAGO ✅" : "PENDENTE ⚠️");
        });
    }

    // --- Método de Pesquisa (CRUD READ com Filtro) ---
    @FXML
    public void filtrarPorCpf() {
        String termo = txtPesquisaCpf.getText();

        if (termo == null || termo.isEmpty()) {
            atualizarLista(); // Se vazio, mostra tudo
            return;
        }

        if (agendamentoRepo != null) {
            List<Agendamento> filtrados = agendamentoRepo.buscarPorCpfPaciente(termo);

            if (filtrados.isEmpty()) {
                mostrarAlerta("Pesquisa", "Nenhum agendamento encontrado para este CPF.");
            }

            tableViewAgendamentos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    @FXML
    public void atualizarLista() {
        if (agendamentoRepo != null) {
            txtPesquisaCpf.clear(); // Limpa o campo de busca ao resetar a lista
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