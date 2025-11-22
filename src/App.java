import controller.Cadastrador;
import controller.ClinicaManager;
import controller.Relatoriador;
import enums.MetodoPagamento;
import model.*;
import repository.AgendamentoRepositorio;
import repository.DentistaRepositorio;
import repository.PacienteRepositorio;
import repository.ProcedimentoRepositorio;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("--- Sistema de Clínica Odontológica (Refatorado) ---");

        // 1. INSTANCIAR REPOSITÓRIOS (Camada de Dados)
        PacienteRepositorio pacienteRepo = new PacienteRepositorio();
        DentistaRepositorio dentistaRepo = new DentistaRepositorio();
        ProcedimentoRepositorio procedimentoRepo = new ProcedimentoRepositorio();
        AgendamentoRepositorio agendamentoRepo = new AgendamentoRepositorio();

        // 2. INSTANCIAR CONTROLADORES COM INJEÇÃO DE DEPENDÊNCIA
        // controller.Cadastrador cuida de Pessoas e Procedimentos
        Cadastrador cadastrador = new Cadastrador(pacienteRepo, dentistaRepo, procedimentoRepo);

        // Manager cuida da Agenda e Regras de Negócio
        ClinicaManager manager = new ClinicaManager(agendamentoRepo, dentistaRepo, pacienteRepo);

        // controller.Relatoriador cuida da visualização
        Relatoriador relatoriador = new Relatoriador(agendamentoRepo);

        // 3. EXECUÇÃO DO CENÁRIO

        // Criar dados
        Paciente p1 = new Paciente("João Silva", "111.222.333-44", "9999-8888", "joao@email.com", "Rua A");
        Dentista d1 = new Dentista("Dr. Carlos", "222.333.444-55", "9888-7777", "carlos@clinica.com", "Av B", "Ortodontia", LocalTime.of(8, 0), LocalTime.of(18, 0));
        Procedimento pr1 = new Procedimento("Limpeza Simples", 150.0, 30, d1);

        // Cadastrar
        cadastrador.cadastrarPaciente(p1);
        cadastrador.cadastrarDentista(d1);
        cadastrador.adicionarProcedimento(pr1); // Note: método renomeado para 'adicionarProcedimento' no controller.Cadastrador novo

        // Agendar
        LocalDateTime dataConsulta = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);

        // Tenta marcar (O manager vai checar no repo se o dentista está livre)
        boolean agendou = manager.marcarAgendamento(p1, d1, pr1, dataConsulta, "Sala 1");

        if (agendou) {
            System.out.println("model.Agendamento realizado com sucesso!");
        }

        // Simular model.Pagamento
        Pagamento pg1 = new Pagamento(pr1.getPreco(), MetodoPagamento.PIX, pr1);
        manager.registrarPagamento(pg1);

        // Gerar Relatório Simples
        System.out.println("--- Relatório do Dr. Carlos ---");
        var relatorio = relatoriador.gerarRelatorioConsultas(d1, LocalDateTime.now(), LocalDateTime.now().plusDays(5));
        System.out.println("Consultas encontradas: " + relatorio.size());
    }
}