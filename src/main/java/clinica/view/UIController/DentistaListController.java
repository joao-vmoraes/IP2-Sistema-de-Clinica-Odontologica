package clinica.view.UIController;

import clinica.model.Dentista;
import clinica.repository.DentistaRepositorio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DentistaListController {

    // IDs dos componentes no FXML
    @FXML private TableView<Dentista> tableViewDentistas;
    @FXML private TableColumn<Dentista, String> colNome;
    @FXML private TableColumn<Dentista, String> colCpf;
    @FXML private TableColumn<Dentista, String> colEmail;

    // Dependência do Repositório (Será injetada)
    private DentistaRepositorio dentistaRepositorio;

    // Setter para injeção de dependência
    public void setDentistaRepositorio(DentistaRepositorio repo) {
        this.dentistaRepositorio = repo;
        // Carrega a lista assim que o repositório é entregue
        carregarListaDentistas();
    }

    @FXML
    public void initialize() {
        // Liga a coluna 'colNome' ao método 'getNome()' do objeto Paciente
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public void carregarListaDentistas() {
        if (dentistaRepositorio != null) {
            List<Dentista> lista = dentistaRepositorio.listarTodos();
            tableViewDentistas.setItems(
                    FXCollections.observableArrayList(lista)
            );
        }
    }
}