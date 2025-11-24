package clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import clinica.repository.PacienteRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.ProcedimentoRepositorio;

import clinica.controller.Cadastrador;
import clinica.model.Paciente;
import clinica.model.Dentista;
import java.time.LocalTime;
import clinica.view.UIController.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. Instanciação da Camada de Dados (Banco de Dados em Memória)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();

        // 2. Instanciação da Camada de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);

        // 3. Dados de Teste (Para não começar com o sistema vazio)
        pacienteRepo.salvar(new Paciente("João Teste", "111.222.333-44", "9999-0000", "joao@email.com", "Rua A"));
        dentistaRepo.salvar(new Dentista("Dr. Silva", "CRM-1234", "9888-7777", "silva@clinica.com", "Rua B", "Ortodontia", LocalTime.of(8,0), LocalTime.of(18,0)));

        // 4. Carregamento da Interface Gráfica
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load();

        // 5. Injeção de Dependência (Passando os serviços para o controlador principal)
        MainController mainController = loader.getController();
        mainController.setServices(pacienteRepo, dentistaRepo, cadastrador);

        // 6. Exibir Janela
        Scene scene = new Scene(root);
        primaryStage.setTitle("Sistema Clínica Odontológica - V1.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}