package clinica.view.UIController;

import clinica.controller.Cadastrador;
import clinica.repository.PacienteRepositorio;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane borderPane;

    // Serviços Injetados
    private PacienteRepositorio pacienteRepo;
    private Cadastrador cadastrador;

    // Método chamado pelo App.java para entregar os serviços
    public void setServices(PacienteRepositorio pRepo, Cadastrador c) {
        this.pacienteRepo = pRepo;
        this.cadastrador = c;

        // Carrega a lista assim que abre o sistema para não ficar vazio
        loadPacienteList();
    }

    // --- Navegação: Lista de Pacientes ---
    @FXML
    public void loadPacienteList() {
        try {
            // Carrega o arquivo FXML da lista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/PacienteList.fxml"));

            // IMPORTANTE: Usamos 'Parent' porque a raiz pode ser VBox, AnchorPane, etc.
            Parent listLayout = loader.load();

            // Injeta o repositório no controlador da lista
            PacienteListController controller = loader.getController();
            controller.setPacienteRepositorio(pacienteRepo);

            // Exibe no centro da tela principal
            borderPane.setCenter(listLayout);

        } catch (IOException e) {
            mostrarErro("Erro ao carregar lista de pacientes", e);
        }
    }

    // --- Navegação: Cadastro de Paciente ---
    @FXML
    public void loadCadastro() {
        try {
            // Carrega o arquivo FXML do cadastro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/CadastroPaciente.fxml"));
            Parent cadastroLayout = loader.load();

            // Injeta o cadastrador no controlador do formulário
            CadastroPacienteController controller = loader.getController();
            controller.setCadastrador(cadastrador);

            // Exibe no centro da tela principal
            borderPane.setCenter(cadastroLayout);

        } catch (IOException e) {
            mostrarErro("Erro ao carregar tela de cadastro", e);
        }
    }

    // Método auxiliar para mostrar erros na tela e no console (ajuda no debug)
    private void mostrarErro(String titulo, Exception e) {
        e.printStackTrace(); // Mostra o erro detalhado no console do IntelliJ
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro do Sistema");
        alert.setHeaderText(titulo);
        alert.setContentText("Detalhes: " + e.getMessage());
        alert.showAndWait();
    }
}