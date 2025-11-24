package clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import clinica.repository.AgendamentoRepositorio;
import clinica.repository.AtendimentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
import clinica.repository.PagamentoRepositorio;
import clinica.repository.ProcedimentoRepositorio;

import clinica.controller.Cadastrador;
import clinica.controller.ClinicaManager;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.view.UIController.MainController;

import java.time.LocalTime;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. Repositórios
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();
        PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();
        AtendimentoRepositorio atendimentoRepo = new AtendimentoRepositorio(); // NOVO

        // 2. Controladores
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        ClinicaManager manager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo);

        // 3. Dados
        pacienteRepo.salvar(new Paciente("João da Silva", "111.222.333-44", "9999-0000", "joao@email.com", "Rua Alfa"));
        pacienteRepo.salvar(new Paciente("Maria Lima", "222.333.444-55", "8888-1111", "maria@email.com", "Av Beta"));
        dentistaRepo.salvar(new Dentista("Dr. Silva", "CRM-1234", "9888-7777", "silva@clinica.com", "Rua B", "Ortodontia", LocalTime.of(8,0), LocalTime.of(18,0)));
        dentistaRepo.salvar(new Dentista("Luiz Marcos", "333.444.555-66", "7777-2222", "luiz@email.com", "Av Gama", "Odontologista", LocalTime.of(9,0), LocalTime.of(17,0)));

        // 4. Tela
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load();

        // 5. Injeção
        MainController mainController = loader.getController();
        // Passa TODOS os repositórios (incluindo atendimentoRepo)
        mainController.setServices(pacienteRepo, dentistaRepo, procedimentoRepo, agendamentoRepo, pagamentoRepo, atendimentoRepo, cadastrador, manager);

        // 6. Exibir
        Scene scene = new Scene(root);
        primaryStage.setTitle("Clínica Odontológica - Sistema de Gestão");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}