package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Procedimento;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProcedimentoListController {

    @FXML private TextField txtFiltroNome;

    @FXML private TableView<Procedimento> tableViewProcedimentos;
    @FXML private TableColumn<Procedimento, String> colNome;
    @FXML private TableColumn<Procedimento, String> colDesc;
    @FXML private TableColumn<Procedimento, String> colPreco;
    @FXML private TableColumn<Procedimento, String> colTempo;

    private Cadastrador cadastrador;

    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        carregarListaProcedimentos();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colDesc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescricao() != null ?
                        cellData.getValue().getDescricao() : "(Sem descrição)"));

        colPreco.setCellValueFactory(cellData ->
                new SimpleStringProperty("R$ " + cellData.getValue().getPreco()));

        colTempo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDuracaoEmMinutos() + " Minutos"));
    }

    public void carregarListaProcedimentos() {
        if (cadastrador != null && tableViewProcedimentos != null) {
            List<Procedimento> lista = cadastrador.listarProcedimentos();
            tableViewProcedimentos.setItems(FXCollections.observableArrayList(lista));
        }
    }
}