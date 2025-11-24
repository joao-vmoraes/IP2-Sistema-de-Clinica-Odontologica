package clinica.view.UIController;

import clinica.enums.MetodoPagamento;
import clinica.model.Agendamento;
import clinica.model.Paciente;
import clinica.model.Pagamento;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.PagamentoRepositorio;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PagamentoController {

    @FXML private ComboBox<Paciente> comboPaciente;
    @FXML private ComboBox<Agendamento> comboAgendamento; // NOVO: Escolher qual conta pagar
    @FXML private TextField txtValor;
    @FXML private ComboBox<MetodoPagamento> comboMetodo;
    @FXML private DatePicker datePickerData;
    @FXML private TextArea txtObs;

    private PagamentoRepositorio pagamentoRepo;
    private PacienteRepositorio pacienteRepo;
    private AgendamentoRepositorio agendamentoRepo; // Precisamos buscar os agendamentos

    public void setDependencies(PagamentoRepositorio pagamentoRepo,
                                PacienteRepositorio pacienteRepo,
                                AgendamentoRepositorio agendamentoRepo) {
        this.pagamentoRepo = pagamentoRepo;
        this.pacienteRepo = pacienteRepo;
        this.agendamentoRepo = agendamentoRepo;
        carregarDados();
    }

    private void carregarDados() {
        if (pacienteRepo != null) {
            comboPaciente.setItems(FXCollections.observableArrayList(pacienteRepo.listarTodos()));

            comboPaciente.setConverter(new StringConverter<Paciente>() {
                @Override public String toString(Paciente p) { return p == null ? "" : p.getNome(); }
                @Override public Paciente fromString(String s) { return null; }
            });
        }

        comboMetodo.setItems(FXCollections.observableArrayList(MetodoPagamento.values()));
        datePickerData.setValue(LocalDate.now());
    }

    @FXML
    private void aoSelecionarPaciente() {
        Paciente p = comboPaciente.getValue();
        if (p != null && agendamentoRepo != null) {
            List<Agendamento> pendencias = agendamentoRepo.buscarPorCpfPaciente(p.getCpf()).stream()
                    .filter(a -> !a.isPago())
                    .collect(Collectors.toList());

            comboAgendamento.setItems(FXCollections.observableArrayList(pendencias));

            if (!pendencias.isEmpty()) {
                comboAgendamento.setPromptText("Selecione a conta a pagar...");
            } else {
                comboAgendamento.setPromptText("Nenhuma pendência encontrada.");
            }
        }
    }

    @FXML
    private void aoSelecionarAgendamento() {
        Agendamento a = comboAgendamento.getValue();
        if (a != null && a.getProcedimento() != null) {
            txtValor.setText(String.valueOf(a.getProcedimento().getPreco()));
        }
    }

    @FXML
    private void handleRegistrar() {
        if (comboAgendamento.getValue() == null || txtValor.getText().isEmpty() || comboMetodo.getValue() == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Atenção", "Selecione o Agendamento (Conta), Valor e Método.");
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));
            Agendamento agendamentoAlvo = comboAgendamento.getValue();
            MetodoPagamento metodo = comboMetodo.getValue();
            Pagamento novoPagamento = new Pagamento(valor, metodo, agendamentoAlvo.getProcedimento());
            novoPagamento.confirmarPagamento();

            pagamentoRepo.salvar(novoPagamento);

            agendamentoAlvo.setPago(true);

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
        comboAgendamento.getItems().clear(); // Limpa a lista de contas
        txtValor.clear();
        comboMetodo.getSelectionModel().clearSelection();
        txtObs.clear();
        datePickerData.setValue(LocalDate.now());
    }

    private void mostrarAlerta(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}