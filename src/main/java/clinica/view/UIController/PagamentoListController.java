package clinica.view.UIController;

import java.time.format.DateTimeFormatter;

import clinica.model.Pagamento;
import clinica.repository.PagamentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class PagamentoListController {

    // IDs dos componentes no FXML
    @FXML private TableView<Pagamento> tableViewPagamentos;
    @FXML private TableColumn<Pagamento, String> colData;
    @FXML private TableColumn<Pagamento, String> colPreco;
    @FXML private TableColumn<Pagamento, String> colMetodo;
    @FXML private TableColumn<Pagamento, String> colAgendamento;

    // Dependência do Repositório (Será injetada)
    private PagamentoRepositorio pagamentoRepositorio;

    // Setter para injeção de dependência
    public void setRepositorio(PagamentoRepositorio repo) {
        this.pagamentoRepositorio = repo;
        // Carrega a lista assim que o repositório é entregue
        carregarListaPagamentos();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDataPagamento().format(formatter)));

        colPreco.setCellValueFactory(cellData ->
                new SimpleStringProperty("R$" + cellData.getValue().getValor()));

        colMetodo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMetodo().toString()));

        colAgendamento.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getReferenciaAgendamento().getProcedimento().getNome()));
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