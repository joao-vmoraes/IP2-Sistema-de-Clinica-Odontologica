package clinica;

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
import clinica.controller.Relatoriador; // Importado
import clinica.enums.DiasSemana;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
import clinica.view.UIController.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. Instanciar Repositórios
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();
        PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();
        AtendimentoRepositorio atendimentoRepo = new AtendimentoRepositorio();

        // 2. Instanciar Controladores de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);

        ClinicaManager clinicaManager = new ClinicaManager(
                agendamentoRepo, dentistaRepo, pacienteRepo, atendimentoRepo, pagamentoRepo
        );

        // NOVO: Instanciar o Relatoriador
        Relatoriador relatoriador = new Relatoriador(agendamentoRepo, pagamentoRepo);

        // 3. (Opcional) Popular com Dados Iniciais para Teste
        inicializarDadosDeTeste(cadastrador);

        // 4. Carregar Interface Gráfica Principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load();

        // 5. Injetar Dependências no MainController
        MainController mainController = loader.getController();
        // Atualizado para passar também o relatoriador
        mainController.setServices(cadastrador, clinicaManager, relatoriador);

        // 6. Configurar e Exibir Palco (Stage)
        Scene scene = new Scene(root, 900, 650);
        primaryStage.setTitle("Sistema de Clínica Odontológica - IP2");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void inicializarDadosDeTeste(Cadastrador cadastrador) {
        Paciente p1 = new Paciente("João da Silva", "111.222.333-44", "9999-8888", "joao@email.com", "Rua Alfa");

        Dentista d1 = new Dentista("Dra. Ana", "555.666.777-88", "8888-9999", "ana@clinica.com", "Rua Beta", "Clínico Geral", LocalTime.of(8,0), LocalTime.of(17,0));
        d1.AdicionarDiaDeFolga(DiasSemana.Domingo);

        Procedimento proc1 = new Procedimento("Extração", 150.00, 60);
        Procedimento proc2 = new Procedimento("Limpeza", 100.00, 30);

        cadastrador.cadastrar(p1);
        cadastrador.cadastrar(d1);
        cadastrador.cadastrar(proc1);
        cadastrador.cadastrar(proc2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}