package com.openboxsoftware.android.mail;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class MailTask extends AsyncTask<Mail, Void, Integer> {

	private Context context;
	private MailSender mailSender;
	private ProgressDialog progressDialog;
	
	public MailTask(Context context, MailSender mailSender) {
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("Sending page...");
		progressDialog.setCanceledOnTouchOutside(false);
		
		this.context = context;
		this.mailSender = mailSender;
	}
	
	@Override
	protected Integer doInBackground(Mail... mails) {
		
		int sent = 0;
		
		for(Mail mail : mails) {
			
			try {
				
				if(mailSender.send(mail)) {
					sent++;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return sent;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog.show();
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		progressDialog.dismiss();
		Toast.makeText(context, "Sent " + result + " page(s) to Edkins.", Toast.LENGTH_SHORT).show();
	}
}
