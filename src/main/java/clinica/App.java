package clinica;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import clinica.model.Agendamento;
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
import clinica.controller.Relatoriador;
import clinica.view.UIController.MainController;
import clinica.controller.Notificador;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // 1. INSTANCIAÇÃO DE REPOSITÓRIOS E SERVIÇOS
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();
        PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();
        AtendimentoRepositorio atendimentoRepo = new AtendimentoRepositorio();

        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        ClinicaManager clinicaManager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo, atendimentoRepo, pagamentoRepo);
        // O ClinicaManager usa DentistaRepo e PacienteRepo em seu construtor, mas eles não estão sendo usados nos métodos que foram incorporados.
        Relatoriador relatoriador = new Relatoriador(agendamentoRepo, pagamentoRepo);
        Notificador notificador = new Notificador(agendamentoRepo);
        // ---------------------------------------------------------------------------------------------------------------------------------------

        // 2. CRIAÇÃO E CADASTRO DOS DADOS DE TESTE (Ordem Corrigida)

        // Paciente: Agora com o e-mail que você quer testar
        Paciente p1 = new Paciente("João da Silva", "111.222.333-44", "9999-8888", "clinicaufrpeodontologica@email.com", "Rua Alfa");

        // Dentista: Usando o construtor correto e adicionando a folga à parte (como no modelo DevGabriel)
        // Nota: O construtor com 9 argumentos exige DiasSemana, não DayOfWeek
        Dentista d1 = new Dentista("Dra. Ana", "555.666.777-88", "8888-9999", "ana@clinica.com", "Rua Beta", "Clínico Geral", LocalTime.of(8,0), LocalTime.of(17,0), DayOfWeek.SUNDAY);

        Procedimento proc = new Procedimento("Extração", 150.00, 60);

        // Primeiro, registre as entidades no Cadastrador (para que o Agendamento possa referenciá-las)
        cadastrador.cadastrar(p1);
        cadastrador.cadastrar(d1);
        cadastrador.cadastrar(proc);

        // Marcação do Agendamento (para amanhã)
        LocalDateTime dataConsulta = LocalDate.now().plusDays(1).atTime(LocalTime.of(10, 0));

        System.out.println("\nTentando marcar agendamento de teste para: " + dataConsulta);
        boolean sucesso = clinicaManager.marcarAgendamento(p1, d1, proc, dataConsulta, "Consultório 1");

        if (sucesso) {
            System.out.println("Agendamento de teste criado e salvo no repositório.");
        } else {
            System.err.println("Falha ao criar o agendamento de teste.");
        }

        // 3. EXECUTA O NOTIFICADOR (AGORA O AGENDAMENTO ESTÁ NO REPOSITÓRIO)
        System.out.println("Executando o Notificador para enviar e-mail...");
        notificador.verificarEEnviarLembretes();
        // ---------------------------------------------------------------------------------------------------------------------------------------

        // 4. INICIALIZAÇÃO DA INTERFACE GRÁFICA
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load();

        MainController mainController = loader.getController();
        mainController.setServices(cadastrador, clinicaManager, relatoriador);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Sistema Clínica Odontológica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}