package com.apirest.disney.Sengrid;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class SengridConfig {
	
	@Value("${app.sendgrid.emailfrom}")
	private static String emailfrom;
	
	@Value("${app.sendgrid.key}")
	private static String SendGridApiKey;
	
	public void enviaremailregistro(String emailto) throws IOException {
		Email from = new Email(emailfrom);
		String subject = "Welcome";
		Email to = new Email(emailto);
		Content content = new Content("text/plain", "Se ha registrado con exito su email");
		Mail mail = new Mail(from, subject, to, content);

		SendGrid sg = new SendGrid(SendGridApiKey);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
}