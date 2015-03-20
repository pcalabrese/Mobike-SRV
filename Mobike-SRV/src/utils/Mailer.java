package utils;


import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Mailer {
	//TODO: inserire dati autenticazione
	final String username = "";
	final String password = "";

	public Mailer() {
		
	}
	
	public void sendMail(String email, String subject, String text){
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			//TODO: inserire mittente
			message.setFrom(new InternetAddress("EMAIL MITTENTE"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(text);
 
			Transport.send(message);
 
			
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	public void sendMailtoList(List<String> emails, String subject, String text){
		
		for(String email : emails){
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
	 
			Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			  });
	 
			try {
	 
				Message message = new MimeMessage(session);
				//TODO: inserire mittente
				message.setFrom(new InternetAddress("EMAIL MITTENTE"));
				message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email));
				message.setSubject(subject);
				message.setText(text);
	 
				Transport.send(message);
	 
				
	 
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		
	}

}
