package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
    
    // As colunas abaixo tiveram a lógica alterada drasticamente
    @FXML private TableColumn<Dentista, String> colExpediente;
    @FXML private TableColumn<Dentista, String> colFolgas;

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

        // --- MUDANÇA 1: Coluna Expediente ---
        // Agora mostra os DIAS que o dentista trabalha (ex: "Seg, Qua, Sex")
        // já que os horários podem variar muito por dia.
        colExpediente.setCellValueFactory(cellData -> {
            Dentista d = cellData.getValue();
            Map<DayOfWeek, List<LocalTime>> grade = d.getGradeDisponibilidade();

            if (grade == null || grade.isEmpty()) {
                return new SimpleStringProperty("Sem grade");
            }

            // Pega as chaves (Dias), ordena e formata para PT-BR
            String diasTrabalhados = grade.keySet().stream()
                .sorted() // Ordena (Segunda vem antes de Terça)
                .map(dia -> dia.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"))) // Ex: "seg", "ter"
                .collect(Collectors.joining(", "));

            return new SimpleStringProperty(diasTrabalhados);
        });

        // --- MUDANÇA 2: Coluna Folgas ---
        // Calcula os dias que NÃO estão no mapa de grade
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
    }

    private void preencherFiltroHorarios() {
        List<String> horarios = new ArrayList<>();
        // Preenche das 08h às 18h (ou o intervalo que preferir)
        for (int h = 8; h <= 18; h++) {
            horarios.add(String.format("%02d:00", h));
        }
        comboFiltroHorario.setItems(FXCollections.observableArrayList(horarios));
    }

    @FXML
    private void aplicarFiltros() {
        if (cadastrador == null) return;

        List<Dentista> todos = cadastrador.listarDentistas();
        List<Dentista> filtrados = new ArrayList<>(todos);

        // 1. Filtro por CPF
        String termoCpf = txtFiltroCpf.getText();
        if (termoCpf != null && !termoCpf.isEmpty()) {
            String termoLimpo = termoCpf.replaceAll("[^0-9]", "");
            filtrados = filtrados.stream()
                    .filter(d -> d.getCpf().replaceAll("[^0-9]", "").contains(termoLimpo))
                    .collect(Collectors.toList());
        }

        // 2. Filtro por Horário (Lógica Nova)
        // Verifica se o dentista tem disponibilidade naquele horário em ALGUM dia da semana
        String horaSelecionadaStr = comboFiltroHorario.getValue();
        if (horaSelecionadaStr != null) {
            LocalTime horaSelecionada = LocalTime.parse(horaSelecionadaStr);
            
            filtrados = filtrados.stream()
                    .filter(d -> {
                        // Varre todos os valores do Map (as listas de horários)
                        // Se qualquer lista conter a hora selecionada, retorna true
                        return d.getGradeDisponibilidade().values().stream()
                                .anyMatch(listaHorarios -> listaHorarios.contains(horaSelecionada));
                    })
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
}