package clinica;

<<<<<<< Updated upstream
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> Stashed changes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import clinica.repository.PacienteRepositorio;
import clinica.repository.DentistaRepositorio;
import clinica.repository.ProcedimentoRepositorio;

import clinica.controller.Cadastrador;
<<<<<<< Updated upstream
import clinica.model.Paciente; // Para adicionar dados de teste
=======
import clinica.controller.ClinicaManager;
import clinica.model.Dentista;
import clinica.model.Paciente;
import clinica.model.Procedimento;
>>>>>>> Stashed changes
import clinica.view.UIController.MainController;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

<<<<<<< Updated upstream
        // --- 1. CONFIGURAÇÃO DA CAMADA DE SERVIÇO (BACK-END) ---

        // Instanciação dos Repositórios (Simulação do Banco de Dados)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();

        // Instanciação dos Controladores de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        // ClinicaManager clinicaManager = new ClinicaManager(...); // Adicione os outros controladores aqui

        // Adicionando dados de teste no Repositório de Pacientes
        pacienteRepo.salvar(new Paciente("João da Silva", "111.222.333-44", "9999-0000", "joao@email.com", "Rua Alfa"));
        pacienteRepo.salvar(new Paciente("Maria Lima", "222.333.444-55", "8888-1111", "maria@email.com", "Av Beta"));


        // --- 2. CARREGAMENTO DO LAYOUT PRINCIPAL (FRONT-END) ---

        // Carrega o FXML do Layout Principal, que contém o menu
=======
        // 1. Inicialização dos Repositórios
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();
        PagamentoRepositorio pagamentoRepo = new PagamentoRepositorio();
        AtendimentoRepositorio atendimentoRepo = new AtendimentoRepositorio();

        // 2. Inicialização dos Controllers de Negócio
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);
        ClinicaManager clinicaManager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo, atendimentoRepo, pagamentoRepo);

        // 3. Criação de Dados Iniciais (Mock Data)
        Paciente p1 = new Paciente("João da Silva", "111.222.333-44", "9999-8888", "joao@email.com", "Rua Alfa");
        Procedimento proc = new Procedimento("Extração", 150.00, 60);

        // --- MUDANÇA AQUI: Instanciação do Dentista ---
        // O construtor antigo (com horários) não existe mais. Usamos o novo:
        Dentista d1 = new Dentista(
            "Dra. Ana", 
            "555.666.777-88", 
            "8888-9999", 
            "ana@clinica.com", 
            "Rua Beta", 
            "Clínico Geral"
        );

        // --- Adicionando uma grade de horários de exemplo para a Dra. Ana ---
        // Simulando que ela atende Seg e Qua, das 08:00 às 10:00
        List<String> horariosAna = new ArrayList<>();
        horariosAna.add("Seg;08:00");
        horariosAna.add("Seg;09:00");
        horariosAna.add("Seg;10:00");
        horariosAna.add("Qua;14:00");
        horariosAna.add("Qua;15:00");
        
        d1.configurarHorarios(horariosAna);

        // 4. Cadastrando os dados no sistema
        cadastrador.cadastrar(p1);
        cadastrador.cadastrar(d1);
        cadastrador.cadastrar(proc);

        // 5. Carregamento da Interface Gráfica (JavaFX)
>>>>>>> Stashed changes
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/MainLayout.fxml"));
        BorderPane root = loader.load(); // O nó raiz do MainLayout.fxml é um BorderPane


        // --- 3. INJEÇÃO DE DEPENDÊNCIA (Ligando Front-end ao Back-end) ---

        // Pega o Controller do FXML (o MainController)
        MainController mainController = loader.getController();

        // Passa os Repositórios e Serviços para o MainController, que irá distribuí-los para as telas filhas
        // O método setServices foi definido para receber apenas o PacienteRepositorio no exemplo anterior.
        // Se precisar passar o Cadastrador, atualize o método setServices no MainController.
        mainController.setServices(pacienteRepo, cadastrador);


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