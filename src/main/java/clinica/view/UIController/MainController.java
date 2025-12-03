package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.controller.Relatoriador;
import clinica.model.Agendamento;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML private BorderPane borderPane;

    private Cadastrador cadastrador;
    private ClinicaManager clinicaManager;
    private Relatoriador relatoriador;

    // Método atualizado para receber também o Relatoriador
    public void setServices(Cadastrador cadastrador, ClinicaManager clinicaManager, Relatoriador relatoriador) {
        this.cadastrador = cadastrador;
        this.clinicaManager = clinicaManager;
        this.relatoriador = relatoriador;
    }

    // --- MÉTODOS DE NAVEGAÇÃO ---

    @FXML
    public void loadCadastroPaciente() {
        carregarTela("/view/fxml/CadastroPaciente.fxml", c -> {
            if (c instanceof CadastroPacienteController)
                ((CadastroPacienteController) c).setCadastrador(cadastrador);
        });
    }

    @FXML
    public void loadCadastroDentista() {
        carregarTela("/view/fxml/CadastroDentista.fxml", c -> {
            if (c instanceof CadastroDentistaController)
                ((CadastroDentistaController) c).setDependencies(cadastrador);
        });
    }

    @FXML
    public void loadCadastroProcedimento() {
        carregarTela("/view/fxml/CadastroProcedimento.fxml", c -> {
            if (c instanceof CadastroProcedimentoController)
                ((CadastroProcedimentoController) c).setDependencies(cadastrador);
        });
    }

    @FXML
    public void loadAgendamento() {
        carregarTela("/view/fxml/Agendamento.fxml", c -> {
            if (c instanceof AgendamentoController)
                ((AgendamentoController) c).setDependencies(clinicaManager, cadastrador, this);
        });
    }

    @FXML
    public void loadPacienteList() {
        carregarTela("/view/fxml/PacienteList.fxml", c -> {
            if (c instanceof PacienteListController)
                ((PacienteListController) c).setDependencies(cadastrador);
        });
    }

    @FXML
    public void loadDentistaList() {
        carregarTela("/view/fxml/DentistaList.fxml", c -> {
            if (c instanceof DentistaListController)
                ((DentistaListController) c).setDependencies(cadastrador);
        });
    }

    @FXML
    public void loadProcedimentoList() {
        carregarTela("/view/fxml/ProcedimentoList.fxml", c -> {
            if (c instanceof ProcedimentoListController)
                ((ProcedimentoListController) c).setDependencies(cadastrador);
        });
    }

    @FXML
    public void loadAgendamentoList() {
        carregarTela("/view/fxml/AgendamentoList.fxml", c -> {
            if (c instanceof AgendamentoListController)
                ((AgendamentoListController) c).setDependencies(clinicaManager, this);
        });
    }

    // --- FINANCEIRO ---

    @FXML
    public void loadPagamento() {
        carregarTela("/view/fxml/Pagamento.fxml", c -> {
            if (c instanceof PagamentoController)
                ((PagamentoController) c).setDependencies(clinicaManager, cadastrador);
        });
    }

    @FXML
    public void loadPagamentoList() {
        carregarTela("/view/fxml/PagamentoList.fxml", c -> {
            if (c instanceof PagamentoListController)
                ((PagamentoListController) c).setDependencies(clinicaManager);
        });
    }

    // --- RELATÓRIOS (NOVO) ---
    @FXML
    public void loadRelatorios() {
        carregarTela("/view/fxml/Relatorio.fxml", c -> {
            if (c instanceof RelatorioController) {
                // CORREÇÃO: Passando 'cadastrador' em vez de 'clinicaManager'
                ((RelatorioController) c).setDependencies(relatoriador, cadastrador);
            }
        });
    }

    // --- TELAS ESPECÍFICAS (Chamadas por código, não por menu) ---

    public void loadAtendimento(Agendamento agendamento) {
        carregarTela("/view/fxml/Atendimento.fxml", c -> {
            if (c instanceof AtendimentoController) {
                ((AtendimentoController) c).setDependencies(agendamento, clinicaManager, this);
            }
        });
    }

    // --- UTILITÁRIO DE CARREGAMENTO ---

    private void carregarTela(String fxmlPath, java.util.function.Consumer<Object> initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent layout = loader.load();

            if (initializer != null) {
                initializer.accept(loader.getController());
            }

            borderPane.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Erro ao carregar tela: " + fxmlPath + "\n" + e.getMessage()).showAndWait();
        }
    }
}