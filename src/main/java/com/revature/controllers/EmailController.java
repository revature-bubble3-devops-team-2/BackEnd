package com.revature.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aspects.annotations.NoAuthIn;
import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.EmailService;
import com.revature.services.ProfileServiceImpl;

import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@CrossOrigin
public class EmailController {

	@Autowired
	EmailService eserv;

	@Autowired
	ProfileServiceImpl pserv;

	
	private static final String EMAIL = "email";
	private static final String URL = "url";
	
	@NoAuthIn
	@PostMapping("/verfied/email")
	public boolean sendEmail(@RequestBody Map<?,?> emailMap) {
		log.info("In Email Controller -----------------------");
		HashMap<String, Object> tempMap = new HashMap<>();
		tempMap.put(EMAIL, emailMap.get(EMAIL));
		log.info((String) emailMap.get(EMAIL));
		tempMap.put(URL, emailMap.get(URL));

		Profile emailProfile = new Profile();
		emailProfile.setEmail((String) emailMap.get(EMAIL));
		Profile profile = pserv.getProfileByEmail(emailProfile);
		log.info(profile);
		tempMap.put("profile", profile);

		try {
			eserv.sendVerificationMessage((String) emailMap.get(EMAIL), "Verify", tempMap);
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (TemplateException e) {
			log.error(e.getMessage());
		} catch (MessagingException e) {
			log.error(e.getMessage());
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

		return pserv.updateProfile(prof)!= null;
	}

	@PostMapping("/email/verify/passwordupdate")
	@NoAuthIn
	public boolean sendEmailForUpdatePassword(@RequestBody Map<?, ?> emailMap) {
		HashMap<String, Object> tempMap = new HashMap<>();
		tempMap.put(EMAIL, emailMap.get(EMAIL));
		tempMap.put(URL, emailMap.get(URL));

		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get(EMAIL));
		Profile profile = pserv.getProfileByEmail(whyGod);
		tempMap.put("profile", profile);

		try {
			eserv.sendPasswordResetMessage((String) emailMap.get(EMAIL), "Verify", tempMap);
			return true;
		} catch (IOException ioE) {
			log.error(ioE.getMessage());
		} catch (TemplateException tE) {
			log.error(tE.getMessage());
		} catch (MessagingException mE) {
			log.error(mE.getMessage());
		}
		return false;

		
		
	}

	@PostMapping("/email/verify/password")
	@NoAuthIn
	public ResponseEntity<ProfileDTO> updatePassword(@RequestBody Map<String, String> pmap) {
		Profile prof = new Profile();
		prof.setEmail(pmap.get(EMAIL));
		prof.setPasskey(pmap.get("password"));
		prof.setVerification(true);

		Profile p = pserv.updatePassword(prof);
		return new ResponseEntity<>(new ProfileDTO(p), HttpStatus.OK);
	}

}
