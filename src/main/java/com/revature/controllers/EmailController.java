package com.revature.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin
public class EmailController {

	@Autowired
	EmailService eserv;
	
	@Autowired
	ProfileService pserv;
	
	private static final String EMAIL = "email";
	
	@NoAuthIn
	@PostMapping("/verfied/email")
	public boolean sendEmail(@RequestBody Map<?,?> emailMap) {
		log.info("In Email Controller -----------------------");
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put(EMAIL, emailMap.get(EMAIL));
		log.info((String) emailMap.get(EMAIL));
		tempMap.put("url", emailMap.get("url"));
		
		
		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get(EMAIL));
		Profile profile = pserv.getProfileByEmail(whyGod);
		log.info(profile);
		tempMap.put("profile", profile);
		
		try {
			eserv.sendVerificationMessage((String) emailMap.get(EMAIL), "Verify", tempMap);
			return true;
		} catch (IOException e) {
			log.info(e.getMessage());
		} catch (TemplateException e) {
			log.info(e.getMessage());
		} catch (MessagingException e) {
			log.info(e.getMessage());
		}
		return false;
	}
	
	@PostMapping("/validate")
	@NoAuthIn
	public boolean emailVerified(@RequestBody String email) {
		log.info("in validate---------------------");
		log.info(EMAIL + ": " + email);
		Profile profile = new Profile();
		profile.setEmail(email);
		
		Profile prof = pserv.getProfileByEmail(profile);
		
		prof.setVerification(true);
		
		if(pserv.updateProfile(prof)!= null) {
		return true;
		}
		return false;
	}
}
