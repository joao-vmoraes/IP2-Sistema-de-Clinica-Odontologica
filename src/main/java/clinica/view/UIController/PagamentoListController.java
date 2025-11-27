package clinica.view.UIController;

import java.time.format.DateTimeFormatter;

import clinica.model.Pagamento;
import clinica.repository.PagamentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class PagamentoListController {

    @FXML private TableView<Pagamento> tableViewPagamentos;
    @FXML private TableColumn<Pagamento, String> colData;
    @FXML private TableColumn<Pagamento, String> colPreco;
    @FXML private TableColumn<Pagamento, String> colMetodo;
    @FXML private TableColumn<Pagamento, String> colAgendamento;

    private PagamentoRepositorio pagamentoRepositorio;

    public void setRepositorio(PagamentoRepositorio repo) {
        this.pagamentoRepositorio = repo;
        // Carrega a lista assim que o repositório é entregue
        carregarListaPagamentos();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colPreco.setCellValueFactory(cellData -> new SimpleStringProperty("R$"+cellData.getValue().getValor()));

        colMetodo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetodo().toString()));

    }

    public void carregarListaPagamentos() {
        if (tableViewPagamentos != null) {
            List<Pagamento> lista = pagamentoRepositorio.listarTodos();
            tableViewPagamentos.setItems(
                    FXCollections.observableArrayList(lista)
            );
        }
    }
}