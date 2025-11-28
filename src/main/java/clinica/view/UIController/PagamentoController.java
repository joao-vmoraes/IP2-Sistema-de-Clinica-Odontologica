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
import java.util.List;
import java.util.stream.Collectors;

public class PagamentoController {

    @FXML private ComboBox<Paciente> comboPaciente;
    @FXML private ComboBox<Agendamento> comboAgendamento;
    @FXML private TextField txtValor;
    @FXML private ComboBox<MetodoPagamento> comboMetodo;
    @FXML private DatePicker datePickerData;
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

        // Conversor para exibir nome do paciente corretamente
        comboPaciente.setConverter(new StringConverter<Paciente>() {
            @Override public String toString(Paciente p) { return p == null ? "" : p.getNome(); }
            @Override public Paciente fromString(String s) { return null; }
        });

        // Quando selecionar paciente, filtrar agendamentos pendentes
        comboPaciente.setOnAction(e -> atualizarAgendamentosPaciente());

        // Quando selecionar agendamento, preencher o valor automaticamente
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
            List<Agendamento> agendamentos = clinicaManager.buscarAgendamentosPorCpfPaciente(p.getCpf())
                    .stream()
                    .filter(a -> !a.isPago()) // Só mostra o que não foi pago
                    .collect(Collectors.toList());

            comboAgendamento.setItems(FXCollections.observableArrayList(agendamentos));
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
                mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione o método e o agendamento.");
                return;
            }

            Pagamento novoPagamento = new Pagamento(valor, metodo, agendamentoAlvo);
            novoPagamento.setDataPagamento(datePickerData.getValue() != null ? datePickerData.getValue().atStartOfDay() : LocalDate.now().atStartOfDay());

            // O Manager cuida de salvar e dar baixa no agendamento
            clinicaManager.registrarPagamento(novoPagamento);

            mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Pagamento registrado! Agendamento quitado.");
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Valor inválido.");
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Falha ao salvar: " + e.getMessage());
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
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}