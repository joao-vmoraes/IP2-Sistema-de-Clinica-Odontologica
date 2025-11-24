package clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import clinica.repository.AgendamentoRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.PacienteRepositorio;
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

        // --- 1. CONFIGURAÇÃO DA CAMADA DE SERVIÇO (BACK-END) ---

        // Instanciação dos Repositórios (Simulação do Banco de Dados)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();

        // Instanciação dos Controladores de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        ClinicaManager manager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo);

        // Adicionando dados de teste nos repositórios (Para não começar com o sistema vazio)
        // Pacientes
        pacienteRepo.salvar(new Paciente("João da Silva", "111.222.333-44", "9999-0000", "joao@email.com", "Rua Alfa"));
        pacienteRepo.salvar(new Paciente("Maria Lima", "222.333.444-55", "8888-1111", "maria@email.com", "Av Beta"));

        // Dentistas
        dentistaRepo.salvar(new Dentista("Dr. Silva", "CRM-1234", "9888-7777", "silva@clinica.com", "Rua B", "Ortodontia", LocalTime.of(8,0), LocalTime.of(18,0)));
        dentistaRepo.salvar(new Dentista("Luiz Marcos", "333.444.555-66", "7777-2222", "luiz@email.com", "Av Gama", "Odontologista", LocalTime.of(9,0), LocalTime.of(17,0)));


        // --- 2. CARREGAMENTO DO LAYOUT PRINCIPAL (FRONT-END) ---

        // Carrega o FXML do Layout Principal, que contém o menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load(); // O nó raiz do MainLayout.fxml é um BorderPane


        // --- 3. INJEÇÃO DE DEPENDÊNCIA (Ligando Front-end ao Back-end) ---

        // Pega o Controller do FXML (o MainController)
        MainController mainController = loader.getController();

        // Passa todos os Repositórios e Serviços para o MainController
        // O MainController irá distribuí-los para as telas filhas (Cadastro, Agendamento, etc.)
        mainController.setServices(pacienteRepo, dentistaRepo, procedimentoRepo, agendamentoRepo, cadastrador, manager);


        // --- 4. EXIBIÇÃO DA JANELA ---

        Scene scene = new Scene(root);
        primaryStage.setTitle("Clínica Odontológica - Sistema de Gestão");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método main que chama o start do JavaFX
    public static void main(String[] args) {
        // Lembre-se que o App.java na raiz é o ponto de entrada agora
        launch(args);
    }
}