package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.enums.MetodoPagamento;
import clinica.model.Agendamento;
import clinica.model.Paciente;
import clinica.model.Pagamento;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Collectors;

public class PagamentoController {

    @FXML private ComboBox<Paciente> comboPaciente;
    @FXML private ComboBox<Agendamento> comboAgendamento;
    @FXML private TextField txtValor;
    @FXML private ComboBox<MetodoPagamento> comboMetodo;
    @FXML private DatePicker datePickerData;

    // MUDANÇA: Substituímos o ComboBox por dois Spinners
    @FXML private Spinner<Integer> spinnerHora;
    @FXML private Spinner<Integer> spinnerMinuto;

    @FXML private TextArea txtObs;

    private ClinicaManager clinicaManager;
    private Cadastrador cadastrador;

    public void setDependencies(ClinicaManager clinicaManager, Cadastrador cadastrador) {
        this.clinicaManager = clinicaManager;
        this.cadastrador = cadastrador;
        carregarCombos();
    }

    @FXML
    public void initialize() {
        comboMetodo.setItems(FXCollections.observableArrayList(MetodoPagamento.values()));

        comboPaciente.setConverter(new StringConverter<Paciente>() {
            @Override public String toString(Paciente p) { return p == null ? "" : p.getNome(); }
            @Override public Paciente fromString(String s) { return null; }
        });

        // 1. CONFIGURAÇÃO DOS SPINNERS (Hora e Minuto)
        // Configura Hora: 0 a 23, valor inicial = hora atual
        SpinnerValueFactory<Integer> valFactoryHora =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, LocalTime.now().getHour());
        spinnerHora.setValueFactory(valFactoryHora);

        // Configura Minuto: 0 a 59, valor inicial = minuto atual
        SpinnerValueFactory<Integer> valFactoryMinuto =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, LocalTime.now().getMinute());
        spinnerMinuto.setValueFactory(valFactoryMinuto);

        atualizarAgendamentosPaciente();
        datePickerData.setValue(LocalDate.now());

        // Listeners
        comboPaciente.setOnAction(e -> atualizarAgendamentosPaciente());
        comboAgendamento.setOnAction(e -> preencherValorAgendamento());
    }

    private void carregarCombos() {
        if(cadastrador != null) {
            comboPaciente.setItems(FXCollections.observableArrayList(cadastrador.listarPacientes()));
        }
    }

    private void atualizarAgendamentosPaciente() {
        Paciente p = comboPaciente.getValue();
        if (p != null && clinicaManager != null) {
            comboAgendamento.setDisable(false);
            comboAgendamento.promptTextProperty().set("Selecione a pendência.");
            var agendamentos = clinicaManager.buscarAgendamentosPorCpfPaciente(p.getCpf())
                    .stream()
                    .filter(a -> !a.isPago())
                    .collect(Collectors.toList());

            comboAgendamento.setItems(FXCollections.observableArrayList(agendamentos));
        } else {
            comboAgendamento.promptTextProperty().set("Selecione um paciente.");
            comboAgendamento.setDisable(true);
        }
    }

    private void preencherValorAgendamento() {
        Agendamento a = comboAgendamento.getValue();
        if (a != null) {
            txtValor.setText(String.valueOf(a.getProcedimento().getPreco()));
        }
    }

    @FXML
    private void handleRegistrar() {
        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            MetodoPagamento metodo = comboMetodo.getValue();
            Agendamento agendamentoAlvo = comboAgendamento.getValue();

            if (metodo == null || agendamentoAlvo == null) {
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Preencha todos os campos.");
                return;
            }

            // 2. CAPTURA DA HORA PRECISA
            LocalDate data = datePickerData.getValue();
            int h = spinnerHora.getValue(); // Pega valor do spinner
            int m = spinnerMinuto.getValue(); // Pega valor do spinner
            LocalTime horaSelecionada = LocalTime.of(h, m);

            LocalDateTime dataHoraPagamento = LocalDateTime.of(data, horaSelecionada);

            Pagamento novoPagamento = new Pagamento(valor, metodo, agendamentoAlvo);
            novoPagamento.setDataPagamento(dataHoraPagamento);

            clinicaManager.registrarPagamento(novoPagamento);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Pagamento registrado! Agendamento quitado.");
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Valor inválido.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelar() {
        limparCampos();
    }

    private void limparCampos() {
        comboPaciente.getSelectionModel().clearSelection();
        comboAgendamento.getItems().clear();
        txtValor.clear();
        comboMetodo.getSelectionModel().clearSelection();
        txtObs.clear();
        datePickerData.setValue(LocalDate.now());

        // Reseta os spinners para a hora atual
        spinnerHora.getValueFactory().setValue(LocalTime.now().getHour());
        spinnerMinuto.getValueFactory().setValue(LocalTime.now().getMinute());
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}