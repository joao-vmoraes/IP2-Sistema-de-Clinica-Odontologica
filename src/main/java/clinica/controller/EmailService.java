package clinica.controller;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    // IMPORTANTE: Para Gmail, você precisa gerar uma "Senha de App" (App Password)
    // nas configurações de segurança da conta Google. A senha normal não funciona mais.
    private final String remetente = "clinicaufrpeodontologica@gmail.com";
    private final String senha = "xsdo ffui pftc ebfx";

    public void enviarEmail(String destinatario, String assunto, String mensagemTexto) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(assunto);
            message.setText(mensagemTexto);

            Transport.send(message);

            System.out.println("E-mail enviado com sucesso para: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Falha ao enviar e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
}