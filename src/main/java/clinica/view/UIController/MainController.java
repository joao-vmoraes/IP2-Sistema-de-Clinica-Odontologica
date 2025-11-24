package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.repository.AgendamentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.PagamentoRepositorio;
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

    // Serviços Injetados (Repositórios)
    private PacienteRepositorio pacienteRepo;
    private DentistaRepositorio dentistaRepo;
    private ProcedimentoRepositorio procedimentoRepo;
    private AgendamentoRepositorio agendamentoRepo;
    private PagamentoRepositorio pagamentoRepo;

    // Controladores de Negócio
    private Cadastrador cadastrador;
    private ClinicaManager clinicaManager;

    // Método Main de Injeção (Chamado pelo App.java)
    public void setServices(PacienteRepositorio pRepo,
                            DentistaRepositorio dRepo,
                            ProcedimentoRepositorio procRepo,
                            AgendamentoRepositorio aRepo,
                            PagamentoRepositorio pagRepo,
                            Cadastrador c,
                            ClinicaManager manager) {
        this.pacienteRepo = pRepo;
        this.dentistaRepo = dRepo;
        this.procedimentoRepo = procRepo;
        this.agendamentoRepo = aRepo;
        this.pagamentoRepo = pagRepo;
        this.cadastrador = c;
        this.clinicaManager = manager;

        // Carrega a tela inicial
        loadPacienteList();
    }

    // --- NAVEGAÇÕES DE CONSULTA ---

    @FXML
    public void loadPacienteList() {
        carregarTela("/view/fxml/PacienteList.fxml", controller -> {
            if (controller instanceof PacienteListController) {
                ((PacienteListController) controller).setPacienteRepositorio(pacienteRepo);
            }
        });
    }

    @FXML
    public void loadAgendamentoList() {
        carregarTela("/view/fxml/AgendamentoList.fxml", controller -> {
            if (controller instanceof AgendamentoListController) {
                ((AgendamentoListController) controller).setAgendamentoRepositorio(agendamentoRepo);
            }
        });
    }

    @FXML
    public void loadDentistaList() {
        // Implementação futura
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Em Breve");
        alert.setHeaderText(null);
        alert.setContentText("A listagem de dentistas será implementada em breve.");
        alert.showAndWait();
    }

    // --- NAVEGAÇÕES DE CADASTRO ---

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
                ((CadastroProcedimentoController) controller).setDependencies(cadastrador, dentistaRepo);
            }
        });
    }

    // --- NAVEGAÇÃO DE AGENDAMENTO ---

    @FXML
    public void loadAgendamento() {
        carregarTela("/view/fxml/Agendamento.fxml", controller -> {
            if (controller instanceof AgendamentoController) {
                // Injeta TUDO que o agendamento precisa, INCLUINDO 'this' (MainController)
                // para permitir voltar para a lista após salvar/cancelar sem fechar o app
                ((AgendamentoController) controller).setDependencies(
                        clinicaManager,
                        pacienteRepo,
                        dentistaRepo,
                        procedimentoRepo,
                        this
                );
            }
        });
    }

    // --- NAVEGAÇÃO FINANCEIRA ---

    @FXML
    public void loadPagamento() {
        carregarTela("/view/fxml/Pagamento.fxml", controller -> {
            if (controller instanceof PagamentoController) {
                // Injeta o repositório de pagamentos e de pacientes
                ((PagamentoController) controller).setDependencies(pagamentoRepo, pacienteRepo, agendamentoRepo);
            }
        });
    }

    // --- MÉTODO AUXILIAR DE CARREGAMENTO ---

    private void carregarTela(String fxmlPath, java.util.function.Consumer<Object> initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent layout = loader.load();

            // Obtém o controlador da tela carregada
            Object controller = loader.getController();

            // Executa a injeção de dependência específica
            initializer.accept(controller);

            // Define a tela no centro do layout principal
            borderPane.setCenter(layout);

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro ao carregar tela");
            alert.setContentText("Não foi possível carregar: " + fxmlPath + "\n" + e.getMessage());
            alert.showAndWait();
        }
    }

    // Alias para compatibilidade com FXML antigo
    @FXML public void loadCadastro() { loadCadastroPaciente(); }
}