package com.revature.services;

import java.io.IOException; 
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;


@Service
public class EmailService {

	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;
	
	@Autowired
	JavaMailSender emailSender;

	public void sendPasswordResetMessage(
	    String to, String subject, Map<String, Object> templateModel)
	        throws IOException, TemplateException, MessagingException {
	        
	    Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
	      .getTemplate("PasswordReset.ftlh");
	    String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

	    sendHtmlMessage(to, subject, htmlBody);
	}
	
	public void sendVerificationMessage(
		    String to, String subject, Map<String, Object> templateModel)
		        throws IOException, TemplateException, MessagingException {
		        
		    Template freemarkerTemplate = freemarkerConfigurer.getConfiguration()
		      .getTemplate("Verification.ftlh");
		    String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

		    sendHtmlMessage(to, subject, htmlBody);
	}

	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}
}