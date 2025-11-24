package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.ProcedimentoRepositorio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML private BorderPane borderPane;

    // Serviços Injetados
    private PacienteRepositorio pacienteRepo;
    private DentistaRepositorio dentistaRepo;
    private ProcedimentoRepositorio procedimentoRepo; // NOVO

    // Controllers de Negócio
    private Cadastrador cadastrador;
    private ClinicaManager clinicaManager; // NOVO

    // Método Main de Injeção (Chamado pelo App.java)
    public void setServices(PacienteRepositorio pRepo,
                            DentistaRepositorio dRepo,
                            ProcedimentoRepositorio procRepo,
                            Cadastrador c,
                            ClinicaManager manager) {
        this.pacienteRepo = pRepo;
        this.dentistaRepo = dRepo;
        this.procedimentoRepo = procRepo;
        this.cadastrador = c;
        this.clinicaManager = manager;

        // Carrega a tela inicial padrão
        loadPacienteList();
    }

    // --- NAVEGAÇÕES ---

    @FXML
    public void loadPacienteList() {
        carregarTela("/view/fxml/PacienteList.fxml", controller -> {
            if (controller instanceof PacienteListController) {
                ((PacienteListController) controller).setPacienteRepositorio(pacienteRepo);
            }
        });
    }

    @FXML
    public void loadDentistaList() {
        // Implementação futura: carregarTela("/view/fxml/DentistaList.fxml", ...);
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Em Breve");
        alert.setHeaderText(null);
        alert.setContentText("A listagem de dentistas será implementada em breve.");
        alert.showAndWait();
    }

    @FXML
    public void loadCadastroPaciente() {
        carregarTela("/view/fxml/CadastroPaciente.fxml", controller -> {
            if (controller instanceof CadastroPacienteController) {
                ((CadastroPacienteController) controller).setCadastrador(cadastrador);
            }
        });
    }

    @FXML
    public void loadCadastroDentista() {
        carregarTela("/view/fxml/CadastroDentista.fxml", controller -> {
            if (controller instanceof CadastroDentistaController) {
                ((CadastroDentistaController) controller).setCadastrador(cadastrador);
            }
        });
    }

    @FXML
    public void loadCadastroProcedimento() {
        carregarTela("/view/fxml/CadastroProcedimento.fxml", controller -> {
            if (controller instanceof CadastroProcedimentoController) {
                // Injeta Cadastrador E DentistaRepositorio (para o ComboBox de dentistas)
                ((CadastroProcedimentoController) controller).setDependencies(cadastrador, dentistaRepo);
            }
        });
    }

    // NOVO: Carregar Tela de Agendamento
    @FXML
    public void loadAgendamento() {
        carregarTela("/view/fxml/Agendamento.fxml", controller -> {
            if (controller instanceof AgendamentoController) {
                // Injeta TUDO que o agendamento precisa para funcionar (Manager + 3 Repos)
                ((AgendamentoController) controller).setDependencies(clinicaManager, pacienteRepo, dentistaRepo, procedimentoRepo);
            }
        });
    }

    // Método genérico para carregar telas e tratar erros
    private void carregarTela(String fxmlPath, java.util.function.Consumer<Object> initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent layout = loader.load();

            Object controller = loader.getController();
            initializer.accept(controller); // Injeta as dependências no controlador filho

            borderPane.setCenter(layout);
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao carregar tela");
            alert.setContentText("Não foi possível carregar: " + fxmlPath + "\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    // Métodos de compatibilidade (Aliases) para o FXML antigo, se necessário
    @FXML public void loadCadastro() { loadCadastroPaciente(); }
}