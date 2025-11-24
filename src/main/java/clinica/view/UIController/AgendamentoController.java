package clinica.view.UIController;

import clinica.controller.ClinicaManager;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.ProcedimentoRepositorio;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
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

    // Dependências
    private ClinicaManager clinicaManager;
    private PacienteRepositorio pacienteRepo;
    private DentistaRepositorio dentistaRepo;
    private ProcedimentoRepositorio procedimentoRepo;

    // Método para injetar TUDO que precisamos
    public void setDependencies(ClinicaManager manager,
                                PacienteRepositorio pRepo,
                                DentistaRepositorio dRepo,
                                ProcedimentoRepositorio procRepo) {
        this.clinicaManager = manager;
        this.pacienteRepo = pRepo;
        this.dentistaRepo = dRepo;
        this.procedimentoRepo = procRepo;

        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        // Preencher Pacientes
        if (pacienteRepo != null) {
            comboPaciente.setItems(FXCollections.observableArrayList(pacienteRepo.listarTodos()));
            configurarComboPaciente();
        }

        // Preencher Dentistas
        if (dentistaRepo != null) {
            comboDentista.setItems(FXCollections.observableArrayList(dentistaRepo.listarTodos()));
            configurarComboDentista();
        }

        // Preencher Procedimentos
        if (procedimentoRepo != null) {
            comboProcedimento.setItems(FXCollections.observableArrayList(procedimentoRepo.listarProcedimentos()));
            configurarComboProcedimento();
        }

        // Preencher Horários (Exemplo: 08:00 às 18:00 de 30 em 30 min)
        List<String> horarios = new ArrayList<>();
        for (int h = 8; h < 18; h++) {
            horarios.add(String.format("%02d:00", h));
            horarios.add(String.format("%02d:30", h));
        }
        comboHorario.setItems(FXCollections.observableArrayList(horarios));
    }

    @FXML
    void handleSalvar(ActionEvent event) {
        if (camposInvalidos()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Preencha Paciente, Dentista, Procedimento, Data e Hora.");
            return;
        }

        Paciente paciente = comboPaciente.getValue();
        Dentista dentista = comboDentista.getValue();
        Procedimento procedimento = comboProcedimento.getValue();
        LocalDate data = datePickerData.getValue();
        String horaStr = comboHorario.getValue();
        String sala = txtObservacao.getText().isEmpty() ? "Consultório 1" : txtObservacao.getText(); // Usa obs como Sala ou define padrão

        // Monta o LocalDateTime
        LocalTime horario = LocalTime.parse(horaStr);
        LocalDateTime dataHoraAgendamento = LocalDateTime.of(data, horario);

        // Chama o Manager para tentar agendar
        boolean sucesso = clinicaManager.marcarAgendamento(paciente, dentista, procedimento, dataHoraAgendamento, sala);

        if (sucesso) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Agendamento realizado com sucesso!");
            fecharJanela();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Não foi possível agendar. Verifique se o dentista está disponível neste horário.");
        }
    }

    @FXML
    void handleCancelar(ActionEvent event) {
        fecharJanela();
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

    private void fecharJanela() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    // --- Configuradores visuais dos ComboBoxes ---
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