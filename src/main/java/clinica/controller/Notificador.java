package clinica.controller;

import clinica.enums.StatusAgendamento;
import clinica.model.Agendamento;
import clinica.repository.AgendamentoRepositorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Notificador {

    private AgendamentoRepositorio agendamentoRepo;
    private EmailService emailService;

    public Notificador(AgendamentoRepositorio agendamentoRepo) {
        this.agendamentoRepo = agendamentoRepo;
        this.emailService = new EmailService();
    }

    public void verificarEEnviarLembretes() {
        System.out.println("Iniciando verificação de agendamentos para amanhã...");

        LocalDate amanha = LocalDate.now().plusDays(1);
        List<Agendamento> todos = agendamentoRepo.listarTodos();
        DateTimeFormatter horaFmt = DateTimeFormatter.ofPattern("HH:mm");

        // Thread separada para não travar a interface gráfica enquanto envia e-mails
        new Thread(() -> {
            for (Agendamento a : todos) {
                // Filtra: Data é amanhã E Status não é cancelado/concluído
                if (a.getDataHora().toLocalDate().equals(amanha) &&
                        a.getStatus() == StatusAgendamento.PLANEJADO) {

                    String emailPaciente = a.getPaciente().getEmail();

                    // Validação simples de e-mail
                    if (emailPaciente != null && !emailPaciente.isEmpty() && emailPaciente.contains("@")) {

                        String assunto = "Lembrete de Consulta - Clínica Odontológica";
                        String mensagem = "Olá, " + a.getPaciente().getNome() + "!\n\n" +
                                "Você tem uma consulta marcada para amanhã (" +
                                amanha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ") às " +
                                a.getDataHora().format(horaFmt) + ".\n\n" +
                                "Procedimento: " + a.getProcedimento().getNome() + "\n" +
                                "Dentista: " + a.getDentista().getNome() + "\n\n" +
                                "Caso não possa comparecer, favor avisar com antecedência.\n" +
                                "Atenciosamente,\nClinica Odontológica da Ufrpe.";

                        emailService.enviarEmail(emailPaciente, assunto, mensagem);
                    }
                }
            }
        }).start();
    }
}