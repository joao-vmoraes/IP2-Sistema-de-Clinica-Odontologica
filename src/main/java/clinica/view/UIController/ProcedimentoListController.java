package clinica.view.UIController;

import javafx.scene.control.TextField;

import clinica.model.Procedimento;
import clinica.repository.ProcedimentoRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class ProcedimentoListController {


    @FXML private TextField txtFiltroNome;

    @FXML private TableView<Procedimento> tableViewProcedimentos;
    @FXML private TableColumn<Procedimento, String> colNome;
    @FXML private TableColumn<Procedimento, String> colDesc;
    @FXML private TableColumn<Procedimento, String> colPreco;
    @FXML private TableColumn<Procedimento, String> colTempo;


    private ProcedimentoRepositorio procedimentoRepositorio;

    public void setRepositorio(ProcedimentoRepositorio repo) {
        this.procedimentoRepositorio = repo;
        // Carrega a lista assim que o repositório é entregue
        carregarListaProcedimentos();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colDesc.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescricao() != null ?
                        cellData.getValue().getDescricao() : "(Sem descrição)"));

        colPreco.setCellValueFactory(cellData ->
                new SimpleStringProperty("R$" + cellData.getValue().getPreco()));

        colTempo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDuracaoEmMinutos() + " Minutos"));
    }

    public void carregarListaProcedimentos() {
        if (tableViewProcedimentos != null) {
            List<Procedimento> lista = procedimentoRepositorio.listarTodos();
            tableViewProcedimentos.setItems(
                    FXCollections.observableArrayList(lista)
            );
        }
    }
}