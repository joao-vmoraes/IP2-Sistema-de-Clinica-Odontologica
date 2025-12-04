package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoController {

    @FXML private ComboBox<Paciente> comboPaciente;
    @FXML private ComboBox<Dentista> comboDentista;
    @FXML private ComboBox<Procedimento> comboProcedimento;
    @FXML private DatePicker datePickerData;
    @FXML private ComboBox<String> comboHorario;
    @FXML private TextArea txtObservacao;
    @FXML private Button btnSalvar;
    @FXML private Button btnCancelar;

    private ClinicaManager clinicaManager;
    private Cadastrador cadastrador;
    private MainController mainController;

    public void setDependencies(ClinicaManager manager, Cadastrador cadastrador, MainController main) {
        this.clinicaManager = manager;
        this.cadastrador = cadastrador;
        this.mainController = main;

        carregarCombos();
    }

    @FXML
    public void initialize() {
        configurarComboPaciente();
        configurarComboDentista();
        configurarComboProcedimento();

        List<String> horarios = new ArrayList<>();
        for (int h = 8; h < 18; h++) {
            horarios.add(String.format("%02d:00", h));
            horarios.add(String.format("%02d:30", h));
        }
        comboHorario.setItems(FXCollections.observableArrayList(horarios));
    }

    private void carregarCombos() {
        if (cadastrador != null) {
            comboPaciente.setItems(FXCollections.observableArrayList(cadastrador.listarPacientes()));
            comboDentista.setItems(FXCollections.observableArrayList(cadastrador.listarDentistas()));
            comboProcedimento.setItems(FXCollections.observableArrayList(cadastrador.listarProcedimentos()));
        }
    }

    @FXML
    private void handleSalvar() {
        if (camposInvalidos()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Incompletos", "Preencha todos os campos obrigatórios.");
            return;
        }

        LocalDate data = datePickerData.getValue();
        LocalTime hora = LocalTime.parse(comboHorario.getValue());
        LocalDateTime dataHora = LocalDateTime.of(data, hora);
        String sala = txtObservacao.getText().isEmpty() ? "Consultório 1" : txtObservacao.getText();

        boolean sucesso = clinicaManager.marcarAgendamento(
                comboPaciente.getValue(),
                comboDentista.getValue(),
                comboProcedimento.getValue(),
                dataHora,
                sala,
                data.getDayOfWeek()
        );

        if (sucesso) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Agendamento realizado!");
            mainController.loadAgendamentoList();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Conflito", "Horário indisponível para este dentista.");
        }
    }

    @FXML
    private void handleCancelar() {
        mainController.loadAgendamentoList();
    }

    private boolean camposInvalidos() {
        return comboPaciente.getValue() == null ||
                comboDentista.getValue() == null ||
                comboProcedimento.getValue() == null ||
                datePickerData.getValue() == null ||
                comboHorario.getValue() == null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void configurarComboPaciente() {
        comboPaciente.setConverter(new StringConverter<Paciente>() {
            @Override public String toString(Paciente p) { return p == null ? "" : p.getNome(); }
            @Override public Paciente fromString(String s) { return null; }
        });
    }
    private void configurarComboDentista() {
        comboDentista.setConverter(new StringConverter<Dentista>() {
            @Override public String toString(Dentista d) { return d == null ? "" : d.getNome(); }
            @Override public Dentista fromString(String s) { return null; }
        });
    }
    private void configurarComboProcedimento() {
        comboProcedimento.setConverter(new StringConverter<Procedimento>() {
            @Override public String toString(Procedimento p) { return p == null ? "" : p.getNome() + " (" + p.getDuracaoEmMinutos() + " min)"; }
            @Override public Procedimento fromString(String s) { return null; }
        });
    }
}