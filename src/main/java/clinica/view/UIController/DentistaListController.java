package clinica.view.UIController;

import clinica.model.Dentista;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import clinica.controller.Cadastrador;
import clinica.enums.DiasSemana;

public class DentistaListController {

    @FXML private TextField txtFiltroCpf;
    @FXML private ComboBox<String> comboFiltroHorario;

    @FXML private TableView<Dentista> tableViewDentistas;
    @FXML private TableColumn<Dentista, String> colNome;
    @FXML private TableColumn<Dentista, String> colCpf;
    @FXML private TableColumn<Dentista, String> colEmail;
    @FXML private TableColumn<Dentista, String> colTelefone;
    @FXML private TableColumn<Dentista, String> colEspecialidade;
    @FXML private TableColumn<Dentista, String> colExpediente;
    @FXML private TableColumn<Dentista, String> colFolgas;
    @FXML private TableColumn<Dentista, Void> colAcoes;

    private Cadastrador cadastrador;

    public void setDependencies(Cadastrador cadastrador) {
        this.cadastrador = cadastrador;
        carregarListaDentistas();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEspecialidade.setCellValueFactory(new PropertyValueFactory<>("especialidade"));

        colExpediente.setCellValueFactory(cellData -> {
            Dentista d = cellData.getValue();
            Map<DayOfWeek, List<LocalTime>> grade = d.getGradeDisponibilidade();

            if (grade == null || grade.isEmpty()) {
                return new SimpleStringProperty("Sem grade");
            }

            String diasTrabalhados = grade.keySet().stream()
                .sorted()
                .map(dia -> dia.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")))
                .collect(Collectors.joining(", "));

            return new SimpleStringProperty(diasTrabalhados);
        });
        
        colFolgas.setCellValueFactory(cellData -> {
            Dentista d = cellData.getValue();
            Map<DayOfWeek, List<LocalTime>> grade = d.getGradeDisponibilidade();
            
            List<String> diasFolga = new ArrayList<>();
            
            // Verifica todos os dias da semana
            for (DayOfWeek dia : DayOfWeek.values()) {
                // Se o dia NÃO está na grade, é folga
                if (grade == null || !grade.containsKey(dia)) {
                    diasFolga.add(dia.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")));
                }
            }

            if (diasFolga.isEmpty()) {
                return new SimpleStringProperty("Atende todos os dias");
            } else if (diasFolga.size() == 7) {
                return new SimpleStringProperty("Sem expediente");
            }

            return new SimpleStringProperty(String.join(", ", diasFolga));
        });

        preencherFiltroHorarios();

        adicionarBotoesAcao();
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
        if (cadastrador == null) return;

        List<Dentista> todos = cadastrador.listarDentistas();
        List<Dentista> filtrados = new ArrayList<>(todos);

        //  Filtro por CPf
        String termoCpf = txtFiltroCpf.getText();
        if (termoCpf != null && !termoCpf.isEmpty()) {
            String termoLimpo = termoCpf.replaceAll("[^0-9]", "");
            filtrados = filtrados.stream()
                    .filter(d -> d.getCpf().replaceAll("[^0-9]", "").contains(termoLimpo))
                    .collect(Collectors.toList());
        }

        DayOfWeek[] diasSemana = DayOfWeek.values();
        for(DayOfWeek dia : diasSemana)
        {
            String horaSelecionada = comboFiltroHorario.getValue();
            if (horaSelecionada != null) {
                LocalTime horario = LocalTime.parse(horaSelecionada);
                filtrados = filtrados.stream()
                        .filter(d -> !horario.isBefore(d.getHorarioTrabalhoInicio(dia)) &&
                                horario.isBefore(d.getHorarioTrabalhoFim(dia)))
                        .collect(Collectors.toList());
            }
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
        if (cadastrador != null) {
            tableViewDentistas.setItems(
                    FXCollections.observableArrayList(cadastrador.listarDentistas())
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

    private void adicionarBotoesAcao() {
        Callback<TableColumn<Dentista, Void>, TableCell<Dentista, Void>> cellFactory = param -> new TableCell<>() {
            private final Button btnDeletar = new Button("Deletar");
            private final HBox pane = new HBox(5, btnDeletar);

            {
                btnDeletar.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                btnDeletar.setOnAction(event -> {
                    Dentista d = getTableView().getItems().get(getIndex());
                    d.setInatividade(true);
                    carregarListaDentistas();
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