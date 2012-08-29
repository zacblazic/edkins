package com.openboxsoftware.android.mail;

public class Mail {
	
	private String sender;
	private String recipient;
	private String subject;
	private String body;
		
	public Mail(String sender, String recipient) {
		
		validateSender(sender);
		validateRecipient(recipient);
		
		this.sender = sender;
		this.recipient = recipient;
		this.subject = new String();
		this.body = new String();
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		validateSender(sender);
		this.sender = sender;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		validateRecipient(recipient);
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		validateSubject(subject);
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		validateBody(body);
		this.body = body;
	}
	
	private void validateRecipient(String recipient) {
		if(recipient == null) {
			throw new IllegalArgumentException("Recipient cannot be null.");
		}
		
		if(recipient.equals("")) {
			throw new IllegalArgumentException("Recipient cannot be empty.");
		}
	}

	private void validateSender(String sender) {
		if(sender == null) {
			throw new IllegalArgumentException("Sender cannot be null.");
		}
		
		if(sender.equals("")) {
			throw new IllegalArgumentException("Sender cannot be empty.");
		}
	}

	private void validateSubject(String subject) {
		if(subject == null) {
			throw new IllegalArgumentException("Subject cannot be null.");
		}
	}

	private void validateBody(String body) {
		if(body == null) {
			throw new IllegalArgumentException("Body cannot be null.");
		}
	}
}
