package encryption;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

import java.nio.charset.StandardCharsets;

public class EmailManager {

    private final String passwordInHTMLFile = "abcd*1234";


    public String createPasswordAndSend(String recipient) {
        String password = createRandomSequence();

        sendEmail(recipient, password);

        return password;
    }

    public String createRandomSequence() {
        Random random = new Random();

        // Characters pool: letters, numbers, and some special symbols
        String charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        
        // Randomly choose a length between 8 and 16
        int length = 16; // (0 to 8) + 8 = 8 to 16

        StringBuilder randomSequence = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charPool.length());
            randomSequence.append(charPool.charAt(index));
        }

        return randomSequence.toString();
    }

    private void sendEmail(String recipient, String password) {
        String emailToSend = getMailPrompt();

        recipient = "danielbarrios2002@gmail.com";
        String emailText = "WHATEVEEERRRR";

        final String ZOHO_HOST = "smtp.zoho.eu";
        final String TLS_PORT = "897";
       
        final String SENDER_USERNAME = "bmoncalvillo@zohomail.eu";
        final String SENDER_PASSWORD = "Du75zJLqbaZ1";

        // protocol properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", ZOHO_HOST); // change to GMAIL_HOST for gmail                                                         // for gmail
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");
        
        // close connection upon quit being sent
        props.put("mail.smtps.quitwait", "false");

        Session session = Session.getInstance(props, null);

        try {
            // create the message
            final MimeMessage msg = new MimeMessage(session);

            // set recipients and content
            msg.setFrom(new InternetAddress(SENDER_USERNAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSubject("Password Recovery");
            msg.setText(emailText, "utf-8", "html");
            msg.setSentDate(new Date());

            // send the mail
            Transport transport = session.getTransport("smtps");
                // send the mail
                transport.connect(ZOHO_HOST, SENDER_USERNAME, SENDER_PASSWORD);
                transport.sendMessage(msg, msg.getAllRecipients());
            
            
            

        } catch (MessagingException e) 
        {
            throw new RuntimeException(e);

        }
    }

    private String getMailPrompt() {
        // Specify the path to HTML file
        try {
            URL url = getClass().getResource("/resources/Email.html");
            File file = new File(url.getPath());

            return readHtmlFile(file);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "ERRROROROROROROR DANI";
    }

    private String readHtmlFile(File file) throws IOException {
        // Use Paths.get to create a Path object from the file path
        Path path = Paths.get(file.getAbsolutePath());

        // Use Files.readAllBytes to read the content of the file into a byte array
        byte[] bytes = Files.readAllBytes(path);

        // Convert the byte array to a string using UTF-8 encoding
        String htmlContent = new String(bytes, StandardCharsets.UTF_8);

        return htmlContent;
    }

    
}