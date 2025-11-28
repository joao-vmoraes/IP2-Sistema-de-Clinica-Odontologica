package clinica.view.UIController;

import clinica.controller.ClinicaManager;
import clinica.model.Pagamento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PagamentoListController {

    @FXML private TableView<Pagamento> tableViewPagamentos;
    @FXML private TableColumn<Pagamento, String> colData;
    @FXML private TableColumn<Pagamento, String> colPreco;
    @FXML private TableColumn<Pagamento, String> colMetodo;
    @FXML private TableColumn<Pagamento, String> colAgendamento;

    private ClinicaManager clinicaManager;

    public void setDependencies(ClinicaManager clinicaManager) {
        this.clinicaManager = clinicaManager;
        carregarListaPagamentos();
    }

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colData.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getDataPagamento() != null ?
                                cellData.getValue().getDataPagamento().format(formatter) : ""
                ));

        colPreco.setCellValueFactory(cellData ->
                new SimpleStringProperty("R$" + cellData.getValue().getValor()));

        colMetodo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getMetodo().toString()));

        colAgendamento.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getReferenciaAgendamento() != null ?
                                cellData.getValue().getReferenciaAgendamento().getPaciente().getNome() : "Avulso"
                ));
    }

    public void carregarListaPagamentos() {
        if (clinicaManager != null) {
            List<Pagamento> lista = clinicaManager.listarPagamentos();
            tableViewPagamentos.setItems(FXCollections.observableArrayList(lista));
        }
    }
}