package clinica.view.UIController;

import clinica.model.Dentista;
import clinica.repository.DentistaRepositorio;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DentistaListController {

    @FXML private TextField txtFiltroCpf;
    @FXML private ComboBox<String> comboFiltroHorario;

    @FXML private TableView<Dentista> tableViewDentistas;
    @FXML private TableColumn<Dentista, String> colNome;
    @FXML private TableColumn<Dentista, String> colCpf;
    @FXML private TableColumn<Dentista, String> colEmail;
    @FXML private TableColumn<Dentista, String> colTelefone;
    @FXML private TableColumn<Dentista, String> colEspecialidade;
    @FXML private TableColumn<Dentista, String> colExpediente; // Nova coluna

    private DentistaRepositorio dentistaRepositorio;

    public void setDentistaRepositorio(DentistaRepositorio repo) {
        this.dentistaRepositorio = repo;
        carregarListaDentistas();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm");
        colExpediente.setCellValueFactory(cellData -> {
            Dentista d = cellData.getValue();
            if (d.getHorarioTrabalhoInicio() != null && d.getHorarioTrabalhoFim() != null) {
                return new SimpleStringProperty(
                        d.getHorarioTrabalhoInicio().format(fmt) + " às " +
                                d.getHorarioTrabalhoFim().format(fmt)
                );
            }
            return new SimpleStringProperty("-");
        });

        preencherFiltroHorarios();
    }

    private void preencherFiltroHorarios() {
        List<String> horarios = new ArrayList<>();
        for (int h = 0; h <= 24; h++) {
            horarios.add(String.format("%02d:00", h));
        }
        comboFiltroHorario.setItems(FXCollections.observableArrayList(horarios));
    }

    @FXML
    private void aplicarFiltros() {
        if (dentistaRepositorio == null) return;

        List<Dentista> todos = dentistaRepositorio.listarTodos();
        List<Dentista> filtrados = new ArrayList<>(todos);

        //  Filtro por CPF
        String termoCpf = txtFiltroCpf.getText();
        if (termoCpf != null && !termoCpf.isEmpty()) {
            String termoLimpo = termoCpf.replaceAll("[^0-9]", "");
            filtrados = filtrados.stream()
                    .filter(d -> d.getCpf().replaceAll("[^0-9]", "").contains(termoLimpo))
                    .collect(Collectors.toList());
        }

        String horaSelecionada = comboFiltroHorario.getValue();
        if (horaSelecionada != null) {
            LocalTime horario = LocalTime.parse(horaSelecionada);
            filtrados = filtrados.stream()
                    .filter(d -> !horario.isBefore(d.getHorarioTrabalhoInicio()) &&
                            horario.isBefore(d.getHorarioTrabalhoFim()))
                    .collect(Collectors.toList());
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
        if (dentistaRepositorio != null) {
            tableViewDentistas.setItems(
                    FXCollections.observableArrayList(dentistaRepositorio.listarTodos())
            );
        }
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}