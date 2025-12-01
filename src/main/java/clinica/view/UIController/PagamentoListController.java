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
        carregarListaPagamentos();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        colData.setCellValueFactory(cellData -> {
            if (cellData.getValue().getDataPagamento() != null) {
                return new SimpleStringProperty(cellData.getValue().getDataPagamento().format(formatter));
            }
            return new SimpleStringProperty("-");
        });

        colPreco.setCellValueFactory(cellData -> new SimpleStringProperty("R$"+cellData.getValue().getValor()));

        colMetodo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMetodo().toString()));

        colAgendamento.setCellValueFactory(cellData -> {
            if (cellData.getValue().getReferenciaAgendamento() != null) {
                return new SimpleStringProperty(cellData.getValue().getReferenciaAgendamento().toString());
            }
            return new SimpleStringProperty("Nenhum Agendamento Associado");
        });
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