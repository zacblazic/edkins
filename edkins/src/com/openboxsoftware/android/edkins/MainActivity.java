package com.openboxsoftware.android.edkins;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;

import com.openboxsoftware.android.mail.Mail;
import com.openboxsoftware.android.mail.MailSender;
import com.openboxsoftware.android.mail.MailTask;

public class MainActivity extends Activity {

	private MailSender mailSender;
	private String name = "Zac Blazic";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create a mail sender
        mailSender = new MailSender("butler.edkins@gmail.com", "obbutler", "smtp.gmail.com", "465", "465");
        
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void sendRequest(View view) {
    	Spinner spinner = (Spinner)findViewById(R.id.spinner_hot_drink);
    	
    	String message = "Hello Edkins,\n\n" + 
    					 "Please prepare the following:\n" + 
    					 "1 X " + spinner.getSelectedItem().toString() + "\n\n" + 
    					 "To be delivered to:\n" +
    					 name + "\n\n" + 
    					 "Hurry along now, old chap!";
    	
    	Mail mail = new Mail("edkins", "gedkins@openboxsoftware.com");
    	mail.setSubject("New Edkins Page: Hot Drink Request");
    	mail.setBody(message);
    	
    	new MailTask(this, mailSender).execute(mail);
    }
}
