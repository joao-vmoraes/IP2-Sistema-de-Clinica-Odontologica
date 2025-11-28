package clinica;

import java.time.LocalDateTime;

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
import clinica.model.Pagamento;
import clinica.model.Procedimento;
import clinica.view.UIController.MainController;

import java.time.LocalTime;

import clinica.enums.MetodoPagamento;
import clinica.enums.StatusAgendamento;
import clinica.model.Agendamento;

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

        // 3. Dados para teste
        Paciente p1 = new Paciente("Seu zé", "213.343.194-62", "4002-8922", "zeDelivery@gmail.com", "Posto Ipiranga");
        Dentista d1 = new Dentista("Jonas Popeye", "420.157.666-69", "5370-6471", "cucabel@gmail.com", "Lugar Nenhum", "Arrancador de dentes", LocalTime.of(4, 30), LocalTime.of(23, 59));
        Procedimento proc = new Procedimento("Clareamento", "Deixar os dentes mais brancos", 80, 30);
        Agendamento loPix = new Agendamento(p1, d1, proc, LocalDateTime.of(2015, 8, 24, 16, 47), "69");
        loPix.setStatus(StatusAgendamento.CONCLUIDO);
        loPix.setPago(true);
        Pagamento pag = new Pagamento(420, MetodoPagamento.PIX, loPix);
        pag.confirmarPagamento();
        pag.realizarPagamento();

        cadastrador.cadastrar(new Paciente("João da Silva", "111.222.333-44", "9999-0000", "joao@email.com", "Rua Alfa"));
        cadastrador.cadastrar(new Paciente("Maria Lima", "222.333.444-55", "8888-1111", "maria@email.com", "Av Beta"));
        cadastrador.cadastrar(p1);
        cadastrador.cadastrar(new Dentista("Dr. Silva", "CRM-1234", "9888-7777", "silva@clinica.com", "Rua B", "Ortodontia", LocalTime.of(8,0), LocalTime.of(18,0)));
        cadastrador.cadastrar(new Dentista("Luiz Marcos", "333.444.555-66", "7777-2222", "luiz@email.com", "Av Gama", "Odontologista", LocalTime.of(9,0), LocalTime.of(17,0)));
        cadastrador.cadastrar(d1);
        cadastrador.cadastrar(new Procedimento("Limpeza", 50, 20));
        cadastrador.cadastrar(proc);
        agendamentoRepo.salvar(loPix);
        pagamentoRepo.salvar(pag); //por algum motivo, a listagem de pagamentos esta sumindo quando se salva algum pagamentos

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