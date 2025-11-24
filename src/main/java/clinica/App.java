package clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import clinica.repository.PacienteRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.ProcedimentoRepositorio;

import java.time.LocalDateTime;
import java.time.LocalTime;

import clinica.controller.Cadastrador;
import clinica.model.Dentista;
import clinica.model.Paciente; // Para adicionar dados de teste
import clinica.view.UIController.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // --- 1. CONFIGURAÇÃO DA CAMADA DE SERVIÇO (BACK-END) ---

        // Instanciação dos Repositórios (Simulação do Banco de Dados)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();

        // Instanciação dos Controladores de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        // ClinicaManager clinicaManager = new ClinicaManager(...); // Adicione os outros controladores aqui

        // Adicionando dados de teste nos repositórios
        pacienteRepo.salvar(new Paciente("João da Silva", "111.222.333-44", "9999-0000", "joao@email.com", "Rua Alfa"));
        pacienteRepo.salvar(new Paciente("Maria Lima", "222.333.444-55", "8888-1111", "maria@email.com", "Av Beta"));
        dentistaRepo.salvar(new Dentista("Luiz Marcos", "333.444.555-66", "7777-2222", "luiz@email.com", "Av Gama", "Orologista", LocalTime.now(), LocalTime.now()));


        // --- 2. CARREGAMENTO DO LAYOUT PRINCIPAL (FRONT-END) ---

        // Carrega o FXML do Layout Principal, que contém o menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load(); // O nó raiz do MainLayout.fxml é um BorderPane


        // --- 3. INJEÇÃO DE DEPENDÊNCIA (Ligando Front-end ao Back-end) ---

        // Pega o Controller do FXML (o MainController)
        MainController mainController = loader.getController();

        // Passa os Repositórios e Serviços para o MainController, que irá distribuí-los para as telas filhas
        // O método setServices foi definido para receber apenas o PacienteRepositorio no exemplo anterior.
        // Se precisar passar o Cadastrador, atualize o método setServices no MainController.
        mainController.setServices(pacienteRepo, dentistaRepo, cadastrador);


        // --- 4. EXIBIÇÃO DA JANELA ---

        Scene scene = new Scene(root);
        primaryStage.setTitle("Clínica Odontológica - Menu Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método main que chama o start do JavaFX
    public static void main(String[] args) {
        // Lembre-se que o App.java na raiz é o ponto de entrada agora
        launch(args);
    }
}