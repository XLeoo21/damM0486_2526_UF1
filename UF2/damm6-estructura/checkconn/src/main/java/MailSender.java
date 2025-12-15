import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailSender {
    private final String username;
    private final String password;
    private final String smtpHost;
    private final int smtpPort;

    public MailSender(String username, String password, String smtpHost, int smtpPort) {
        this.username = username;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }

    // Example usage
    public static void main(String[] args) {
        // Replace these with your actual email credentials and SMTP settings
        String username = "your.email@gmail.com";
        String password = "your-app-specific-password";
        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        MailSender sender = new MailSender(username, password, smtpHost, smtpPort);

        try {
            sender.sendEmail(
                    "recipient@example.com",
                    "Connection Status Alert",
                    "Internet connection status has changed. Check the logs for details.");
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}