package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Paciente;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class PacienteListController {

    @FXML private TableView<Paciente> tableViewPacientes;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCpf;
    @FXML private TableColumn<Paciente, String> colEmail;

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
    }

    public void carregarListaPacientes() {
        if (cadastrador != null) {
            List<Paciente> lista = cadastrador.listarPacientes();
            tableViewPacientes.setItems(FXCollections.observableArrayList(lista));
        }
    }
}