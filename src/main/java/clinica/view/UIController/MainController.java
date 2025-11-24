package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.AtendimentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.PagamentoRepositorio;
import clinica.repository.ProcedimentoRepositorio;
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

    // Serviços Injetados (Repositórios)
    private PacienteRepositorio pacienteRepo;
    private DentistaRepositorio dentistaRepo;
    private ProcedimentoRepositorio procedimentoRepo;
    private AgendamentoRepositorio agendamentoRepo;
    private PagamentoRepositorio pagamentoRepo;
    private AtendimentoRepositorio atendimentoRepo;

    // Controladores de Negócio
    private Cadastrador cadastrador;
    private ClinicaManager clinicaManager;

    public void setServices(PacienteRepositorio pRepo,
                            DentistaRepositorio dRepo,
                            ProcedimentoRepositorio procRepo,
                            AgendamentoRepositorio aRepo,
                            PagamentoRepositorio pagRepo,
                            AtendimentoRepositorio atdRepo,
                            Cadastrador c,
                            ClinicaManager manager) {
        this.pacienteRepo = pRepo;
        this.dentistaRepo = dRepo;
        this.procedimentoRepo = procRepo;
        this.agendamentoRepo = aRepo;
        this.pagamentoRepo = pagRepo;
        this.atendimentoRepo = atdRepo;
        this.cadastrador = c;
        this.clinicaManager = manager;

        loadPacienteList();
    }

    // --- NAVEGAÇÕES ---

    @FXML public void loadPacienteList() {
        carregarTela("/view/fxml/PacienteList.fxml", c -> {
            if (c instanceof PacienteListController) ((PacienteListController) c).setPacienteRepositorio(pacienteRepo);
        });
    }

    @FXML public void loadAgendamentoList() {
        carregarTela("/view/fxml/AgendamentoList.fxml", c -> {
            if (c instanceof AgendamentoListController) {
                // Passamos 'this' para que a lista possa chamar loadAtendimento
                ((AgendamentoListController) c).setDependencies(agendamentoRepo, this);
            }
        });
    }

    @FXML public void loadProcedimentoList() {
        carregarTela("/view/fxml/ProcedimentoList.fxml", c -> {
            if (c instanceof ProcedimentoListController) {
                ((ProcedimentoListController) c).setRepositorio(procedimentoRepo);
            }
        });
    }

    @FXML public void loadPagamentoList() {
        carregarTela("/view/fxml/PagamentoList.fxml", c -> {
            if (c instanceof PagamentoListController) {
                ((PagamentoListController) c).setRepositorio(pagamentoRepo);
            }
        });
    }

    @FXML public void loadDentistaList() {
        carregarTela("/view/fxml/DentistaList.fxml", c -> {
            if (c instanceof DentistaListController) ((DentistaListController) c).setDentistaRepositorio(dentistaRepo);
        });
    }

    @FXML public void loadCadastroPaciente() {
        carregarTela("/view/fxml/CadastroPaciente.fxml", c -> {
            if (c instanceof CadastroPacienteController) ((CadastroPacienteController) c).setCadastrador(cadastrador);
        });
    }

    @FXML public void loadCadastroDentista() {
        carregarTela("/view/fxml/CadastroDentista.fxml", c -> {
            if (c instanceof CadastroDentistaController) ((CadastroDentistaController) c).setCadastrador(cadastrador);
        });
    }

    @FXML public void loadCadastroProcedimento() {
        carregarTela("/view/fxml/CadastroProcedimento.fxml", c -> {
            if (c instanceof CadastroProcedimentoController) ((CadastroProcedimentoController) c).setDependencies(cadastrador);
        });
    }

    @FXML public void loadAgendamento() {
        carregarTela("/view/fxml/Agendamento.fxml", c -> {
            if (c instanceof AgendamentoController) ((AgendamentoController) c).setDependencies(clinicaManager, pacienteRepo, dentistaRepo, procedimentoRepo, this);
        });
    }

    @FXML public void loadPagamento() {
        carregarTela("/view/fxml/Pagamento.fxml", c -> {
            if (c instanceof PagamentoController) ((PagamentoController) c).setDependencies(pagamentoRepo, pacienteRepo, agendamentoRepo);
        });
    }

    // Carregar Tela de Atendimento para um agendamento específico
    public void loadAtendimento(Agendamento agendamento) {
        carregarTela("/view/fxml/Atendimento.fxml", c -> {
            if (c instanceof AtendimentoController) {
                ((AtendimentoController) c).setDependencies(agendamento, atendimentoRepo, this);
            }
        });
    }

    private void carregarTela(String fxmlPath, java.util.function.Consumer<Object> initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent layout = loader.load();
            initializer.accept(loader.getController());
            borderPane.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Erro ao carregar: " + fxmlPath + "\n" + e.getMessage()).showAndWait();
        }
    }

    @FXML public void loadCadastro() { loadCadastroPaciente(); }
}