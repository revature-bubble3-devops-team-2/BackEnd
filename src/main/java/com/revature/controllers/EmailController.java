package com.revature.controllers;

import java.io.IOException;
import java.util.HashMap;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.models.Profile;
import com.revature.services.EmailService;
import com.revature.services.ProfileService;

import freemarker.template.TemplateException;

@RestController
@CrossOrigin
public class EmailController {

	@Autowired
	EmailService eserv;
	
	@Autowired
	ProfileService pserv;
	
	@NoAuthIn
	@PostMapping("/verfied/email")
	public boolean sendEmail(@RequestBody HashMap emailMap) {
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("email", emailMap.get("email"));
		tempMap.put("url", emailMap.get("url"));
		
		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get("email"));
		tempMap.put("profile", pserv.getProfileByEmail(whyGod));
		
		try {
			eserv.sendVerificationMessage((String) emailMap.get("email"), "Verify", tempMap);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
