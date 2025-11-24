package clinica.view.UIController;

import clinica.model.Agendamento;
import clinica.repository.AgendamentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.format.DateTimeFormatter;

public class AgendamentoListController {

    @FXML private TableView<Agendamento> tableViewAgendamentos;
    @FXML private TableColumn<Agendamento, String> colDataHora;
    @FXML private TableColumn<Agendamento, String> colPaciente;
    @FXML private TableColumn<Agendamento, String> colDentista;
    @FXML private TableColumn<Agendamento, String> colProcedimento;
    @FXML private TableColumn<Agendamento, String> colStatus;

    private AgendamentoRepositorio agendamentoRepo;

    public void setAgendamentoRepositorio(AgendamentoRepositorio repo) {
        this.agendamentoRepo = repo;
        atualizarLista();
    }

    @FXML
    public void initialize() {
        // Formatação de Data e Hora para String legível
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colDataHora.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataHora().format(formatter)));

        // Extraindo nomes dos objetos relacionados
        colPaciente.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPaciente().getNome()));

        colDentista.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDentista().getNome()));

        colProcedimento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProcedimento() != null ?
                        cellData.getValue().getProcedimento().getNome() : "-"));

        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    @FXML
    public void atualizarLista() {
        if (agendamentoRepo != null) {
            tableViewAgendamentos.setItems(
                    FXCollections.observableArrayList(agendamentoRepo.listarTodos())
            );
        }
    }
}