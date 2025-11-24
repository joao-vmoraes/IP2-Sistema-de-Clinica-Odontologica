package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
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
    private Cadastrador cadastrador;

    // Método chamado pelo App.java para receber TODAS as dependências
    public void setServices(PacienteRepositorio pRepo, DentistaRepositorio dRepo, Cadastrador c) {
        this.pacienteRepo = pRepo;
        this.dentistaRepo = dRepo;
        this.cadastrador = c;

        // Carrega a lista de pacientes ao iniciar o sistema
        loadPacienteList();
    }

    // --- Navegação: Lista de Pacientes ---
    @FXML
    public void loadPacienteList() {
        carregarTela("/view/fxml/PacienteList.fxml", controller -> {
            if (controller instanceof PacienteListController) {
                ((PacienteListController) controller).setPacienteRepositorio(pacienteRepo);
            }
        });
    }

    // --- Navegação: Cadastro de Paciente ---
    @FXML
    public void loadCadastro() {
        carregarTela("/view/fxml/CadastroPaciente.fxml", controller -> {
            if (controller instanceof CadastroPacienteController) {
                ((CadastroPacienteController) controller).setCadastrador(cadastrador);
            }
        });
    }

    // --- Navegação: Cadastro de Procedimento (O NOVO MÉTODO) ---
    @FXML
    public void loadCadastroProcedimento() {
        carregarTela("/view/fxml/CadastroProcedimento.fxml", controller -> {
            if (controller instanceof CadastroProcedimentoController) {
                // Injeta tanto o Cadastrador quanto o Repositório de Dentistas (para o ComboBox)
                ((CadastroProcedimentoController) controller).setDependencies(cadastrador, dentistaRepo);
            }
        });
    }

    // Método genérico para carregar telas, evitar repetição de código e tratar erros
    private void carregarTela(String fxmlPath, java.util.function.Consumer<Object> initializer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent layout = loader.load();

            // Pega o controller da tela que acabou de ser carregada
            Object controller = loader.getController();

            // Executa a configuração específica (injeção de dependência) passada por parâmetro
            initializer.accept(controller);

            // Exibe a tela no centro do Layout Principal
            borderPane.setCenter(layout);

        } catch (IOException e) {
            e.printStackTrace(); // Mostra o erro no console para facilitar o debug
            Alert alert = new Alert(AlertType.ERROR, "Erro ao carregar tela: " + fxmlPath + "\n" + e.getMessage());
            alert.showAndWait();
        }
    }
}