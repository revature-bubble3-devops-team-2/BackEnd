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

	/*
	 * This function create a rest_password message at user request to change password
	 * The instruction send to the user email for changing password
	 * 
	 * @throws IOException occur whenever an input or output operation is failed or interpreted.
	 * @throws TemplateException A runtime exception in a template
	 * @throws MessagingException thrown when the connect method on a Store or Transport object fails due to an authentication failure 
	 */
	public void sendPasswordResetMessage(String to, String subject, Map<String, Object> templateModel)
			throws IOException, TemplateException, MessagingException {

		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("PasswordReset.ftl");
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

		sendHtmlMessage(to, subject, htmlBody);
	}
	
	/*
	 * This function create a message for user to verify his/her email address
	 * The message will send to the user at their email address
	 * 
	 * @throws IOException occur whenever an input or output operation is failed or interpreted.
	 * @throws TemplateException A runtime exception in a template
	 * @throws MessagingException thrown when the connect method on a Store or Transport object fails due to an authentication failure
	 */
	public void sendVerificationMessage(String to, String subject, Map<String, Object> templateModel)
			throws IOException, TemplateException, MessagingException {

		Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("Verification.ftl");
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);

		sendHtmlMessage(to, subject, htmlBody);
	}
	
	/*
	 * Here a new message is created
	 * With MessageHelper we set, who to send the message, set the subject of our message, set the message body
	 * By using JavaMailSender we send the message
	 * 
	 * @throws MessagingException thrown when the connect method on a Store or Transport object fails due to an authentication failure
	 */
	private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}
}