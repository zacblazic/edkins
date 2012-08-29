package com.openboxsoftware.android.mail;

public class TestMailSender {

	public static void main(String[] args) throws Exception {
		
		MailSender ms = new MailSender("zsacblazic@gmail.com", "!BA#cr#27*57", "smtp.gmail.com", "465", "465");
		
		Mail m = new Mail("zacblazic", "zacblazic@gmail.com");
		m.setSubject("Testing Android Email");
		m.setBody("This is a message that goes into the body.");
		ms.send(m);
	}
}
