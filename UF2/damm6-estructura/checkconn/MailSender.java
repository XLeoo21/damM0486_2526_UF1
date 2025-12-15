import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailSender {
    public static void sendPlain(
            String host, int port,
            final String username, final String password,
            String from, String to,
            String subject, String body) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // TLS
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));
        // props.put("mail.smtp.ssl.trust", host); // uncomment if needed

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setText(body);
        msg.setHeader("X-Mailer", "JavaMail");
        msg.setSentDate(new java.util.Date());

        Transport.send(msg);
        System.out.println("Email sent.");
    }


}