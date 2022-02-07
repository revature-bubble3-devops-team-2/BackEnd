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
	public boolean sendEmail(@RequestBody Map<?, ?> emailMap) {
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("email", emailMap.get("email"));
		tempMap.put("url", emailMap.get("url"));

		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get("email"));
		Profile profile = pserv.getProfileByEmail(whyGod);
		tempMap.put("profile", profile);

		try {

			eserv.sendVerificationMessage((String) emailMap.get("email"), "Verify", tempMap);
			return true;
		} catch (IOException e) {

		} catch (TemplateException e) {

		} catch (MessagingException e) {

		}
		return false;
	}

	@PostMapping("/validate")
	@NoAuthIn
	public boolean emailVerified(@RequestBody String email) {
		Profile profile = new Profile();
		profile.setEmail(email);

		Profile prof = pserv.getProfileByEmail(profile);
		prof.setVerification(true);
		Profile p = pserv.updateProfile(prof);

		if (p != null) {
			return true;
		}
		return false;
	}

	@PostMapping("/email/verify/passwordupdate")
	@NoAuthIn
	public boolean sendEmailForUpdatePassword(@RequestBody Map<?, ?> emailMap) {
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("email", emailMap.get("email"));
		tempMap.put("url", emailMap.get("url"));

		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get("email"));
		Profile profile = pserv.getProfileByEmail(whyGod);
		tempMap.put("profile", profile);

		try {
			eserv.sendPasswordResetMessage((String) emailMap.get("email"), "Verify", tempMap);
			return true;
		} catch (IOException e) {

		} catch (TemplateException e) {

		} catch (MessagingException e) {

		}
		return false;
	}

	@PostMapping("/email/verify/password")
	@NoAuthIn
	public ResponseEntity<?> updatePassword(@RequestBody Map<String, String> pmap) {
		Profile prof = new Profile();
		prof.setEmail(pmap.get("email"));
		prof.setPasskey(pmap.get("password"));
		prof.setVerification(true);

		Profile p = pserv.updateProfile(prof);
		return new ResponseEntity<>(new ProfileDTO(p), HttpStatus.OK);
	}

}
