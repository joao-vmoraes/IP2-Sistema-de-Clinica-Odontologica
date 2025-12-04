package clinica;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
import clinica.enums.DiasSemana;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.view.UIController.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();
        PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();
        AtendimentoRepositorio atendimentoRepo = new AtendimentoRepositorio();


        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);

        ClinicaManager clinicaManager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo, atendimentoRepo, pagamentoRepo);

        Paciente p1 = new Paciente("João da Silva", "111.222.333-44", "9999-8888", "joao@email.com", "Rua Alfa");
        Dentista d1 = new Dentista("Dra. Ana", "555.666.777-88", "8888-9999", "ana@clinica.com", "Rua Beta", "Clínico Geral", LocalTime.of(8,0), LocalTime.of(17,0), DayOfWeek.SUNDAY);
        Procedimento proc = new Procedimento("Extração", 150.00, 60);

        cadastrador.cadastrar(p1);
        cadastrador.cadastrar(d1);
        cadastrador.cadastrar(proc);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load();

        MainController mainController = loader.getController();
        mainController.setServices(cadastrador, clinicaManager);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema Clínica Odontológica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}