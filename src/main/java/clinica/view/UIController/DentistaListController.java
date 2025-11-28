package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalTime;
import java.util.List;

public class DentistaListController {

    @FXML private TextField txtFiltroCpf;
    @FXML private ComboBox<String> comboFiltroHorario;

    @FXML private TableView<Dentista> tableViewDentistas;
    @FXML private TableColumn<Dentista, String> colNome;
    @FXML private TableColumn<Dentista, String> colCpf;
    @FXML private TableColumn<Dentista, String> colEmail;
    @FXML private TableColumn<Dentista, String> colTelefone;
    @FXML private TableColumn<Dentista, String> colEspecialidade;
    @FXML private TableColumn<Dentista, String> colExpediente;

    private Cadastrador cadastrador;

    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        carregarListaDentistas();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        colExpediente.setCellFactory(column -> new TableCell<Dentista, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    Dentista d = (Dentista) getTableRow().getItem();
                    setText(d.getHorarioTrabalhoInicio() + " - " + d.getHorarioTrabalhoFim());
                }
            }
        });

        for (int i = 8; i < 18; i++) {
            comboFiltroHorario.getItems().add(String.format("%02d:00", i));
        }
    }

    @FXML
    private void aplicarFiltros() {
        if (cadastrador == null) return;

        // Começa com todos
        List<Dentista> filtrados = cadastrador.listarDentistas();

        String termo = txtFiltroCpf.getText();
        if (termo != null && !termo.isEmpty()) {
            filtrados = cadastrador.buscarDentistasPorCpfParcial(termo);
        }

        String horaSelecionada = comboFiltroHorario.getValue();
        if (horaSelecionada != null) {
            LocalTime horario = LocalTime.parse(horaSelecionada);
            // Re-filtra a lista atual
            filtrados = cadastrador.buscarDentistasPorDisponibilidade(horario);
        }

        tableViewDentistas.setItems(FXCollections.observableArrayList(filtrados));

        if (filtrados.isEmpty()) {
            mostrarAlerta("Filtros", "Nenhum dentista encontrado com esses critérios.");
        }
    }

    @FXML
    private void limparFiltros() {
        txtFiltroCpf.clear();
        comboFiltroHorario.getSelectionModel().clearSelection();
        carregarListaDentistas();
    }

    public void carregarListaDentistas() {
        if (cadastrador != null) {
            tableViewDentistas.setItems(
                    FXCollections.observableArrayList(cadastrador.listarDentistas())
            );
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}