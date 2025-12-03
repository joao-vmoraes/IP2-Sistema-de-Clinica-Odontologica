package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Paciente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;

import javafx.scene.control.TableCell;

public class PacienteListController {

    @FXML private TableView<Paciente> tableViewPacientes;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCpf;
    @FXML private TableColumn<Paciente, String> colEmail;
    @FXML private TableColumn<Paciente, Void> colAcoes;

    private Cadastrador cadastrador;

    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        carregarListaPacientes();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        adicionarBotoesAcao();
    }

    public void carregarListaPacientes() {
        if (cadastrador != null) {
            List<Paciente> lista = cadastrador.listarPacientes();
            tableViewPacientes.setItems(FXCollections.observableArrayList(lista));
        }
    }

    private void adicionarBotoesAcao() {
        Callback<TableColumn<Paciente, Void>, TableCell<Paciente, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnDeletar = new Button("Deletar");
            private final HBox pane = new HBox(5, btnDeletar);

            {
                btnDeletar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                btnDeletar.setOnAction(event -> {
                    Paciente p = getTableView().getItems().get(getIndex());
                    p.setInatividade(true);
                    carregarListaPacientes();
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
}