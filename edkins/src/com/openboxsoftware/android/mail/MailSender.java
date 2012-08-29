package com.openboxsoftware.android.mail;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MailSender extends Authenticator {

	private String username;
	private String password;
	private String host;
	private String port;
	private String socketFactoryPort;
	private boolean auth;
	private Multipart multipart;
	
	public MailSender() {

		auth = true;
		multipart = new MimeMultipart();
		
		MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap(); 
	    mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html"); 
	    mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml"); 
	    mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain"); 
	    mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed"); 
	    mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822"); 
	    CommandMap.setDefaultCommandMap(mc); 
	}
	
	//Validation required
	public MailSender(String username, String password) {
		this();
		
		this.username = username;
		this.password = password;
	}
	
	//Validation required
	public MailSender(String username, String password, String host, String port, String socketFactoryPort) {
		this(username, password);
		
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.socketFactoryPort = socketFactoryPort;
	}
	
	public boolean send(Mail mail) throws AddressException, MessagingException {
		
		// Need to validate properties + username and password
		Properties props = packProperties();
		
		if(!isValidMail(mail)) {
			return false;
		}
		
		Session session = Session.getInstance(props, this);
		
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mail.getSender()));
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(mail.getRecipient()));
		message.setSubject(mail.getSubject());
		message.setSentDate(new Date());
		
		// Create the message body
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(mail.getBody());
		multipart.addBodyPart(messageBodyPart);
		
		// Add the body to the message
		message.setContent(multipart);
		
		// Send the message
		Transport.send(message);
		
		return true;
	}
	
	private Properties packProperties() {
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketFactory.port", socketFactoryPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		
		if(auth) {
			props.put("mail.smtp.auth", "true");
		}
		
		return props;
	}
	
	private boolean isValidMail(Mail mail) {
		
		// Make sure that everything has been initialized
		if(mail.getRecipient() == null || 
				mail.getSender() == null || 
				mail.getSubject() == null || 
				mail.getBody() == null) {
			return false;
		}
		
		// Recipient cannot be empty
		if(mail.getRecipient().length() <= 0) {
			return false;
		}
			
		return true;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
