
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
public class EmailSender{ 

	public static void send(final String from,final String password,String to,String sub,String msg){
	Properties properties = new Properties(); 
	
	properties.put("mail.smtp.auth", "true");
	properties.put("mail.smtp.starttls.enable", "true");
	properties.put("mail.smtp.host", "smtp.gmail.com");
	properties.put("mail.smtp.port", "587");

	
	Session session = Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication(from,password);
		}
	});
	// default session 
	try { 
		MimeMessage message = new MimeMessage(session); // email message 
		message.setFrom(new InternetAddress(from)); // setting header fields 
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 
		message.setSubject(sub); // subject line 
		// actual mail body 
		message.setText(msg);
		// Send message 
		Transport.send(message);
		System.out.println("Email Sent successfully...."); 
		} 
catch (MessagingException mex)
		{
		mex.printStackTrace();
		}
	} 
 
}