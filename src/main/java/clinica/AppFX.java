package clinica;

import clinica.controller.Cadastrador;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.ProcedimentoRepositorio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.UIController.CadastroPacienteController;

public class AppFX extends Application {

    // Método principal do JavaFX
    @Override
    public void start(Stage primaryStage) throws Exception {

        // --- 1. INICIALIZAÇÃO: Criando os serviços (Injeção de Dependência Reverso) ---

        // Criação dos Repositórios (O "Banco de Dados" em memória)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();

        // Criação do Serviço de Negócio (O Controller de Fatos)
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);

        // --- 2. CARREGAMENTO DO FXML ---

        // O caminho aponta para onde você colocou o arquivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/CadastroPaciente.fxml"));
        VBox root = loader.load();

        // --- 3. INJEÇÃO DE DEPENDÊNCIA (A Ponte Essencial) ---

        // Pega a instância do Controller da UI que o FXML acabou de criar
        CadastroPacienteController uiController = loader.getController();

        // Chama o método setter para entregar a instância do serviço Cadastrador
        uiController.setCadastrador(cadastrador);

        // --- 4. CONFIGURAÇÃO DA JANELA ---

        Scene scene = new Scene(root);
        primaryStage.setTitle("Clínica Odontológica - Cadastro");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método Main padrão para inicialização do JavaFX
    public static void main(String[] args) {
        launch(args);
    }
}