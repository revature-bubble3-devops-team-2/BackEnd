package com.revature.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	public boolean sendEmail(@RequestBody Map<?,?> emailMap) {
		System.out.println("In Email Controller -----------------------");
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("email", emailMap.get("email"));
		System.out.println((String) emailMap.get("email"));
		tempMap.put("url", emailMap.get("url"));
		System.out.println(emailMap.get("url"));
		
		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get("email"));
		Profile profile = pserv.getProfileByEmail(whyGod);
		System.out.println(profile);
		tempMap.put("profile", profile);
		
		try {
			eserv.sendVerificationMessage((String) emailMap.get("email"), "Verify", tempMap);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
		
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			
		}
		return false;
	}
	
	@PostMapping("/validate")
	@NoAuthIn
	public boolean emailVerified(@RequestBody String email) {
		System.out.println("in validate---------------------");
		System.out.println("email: " + email);
		Profile profile = new Profile();
		profile.setEmail(email);
		
		Profile prof = pserv.getProfileByEmail(profile);
		
		prof.setVerification(true);
		
		Profile p = pserv.updateProfile(prof);
		
		if(p!= null) {
			
			System.out.println(p.isVerification());
		return true;
		}
		return false;
	}
	
	@PostMapping("/email/verify/passwordupdate")
	@NoAuthIn
	public boolean sendEmailForUpdatePassword(@RequestBody Map<?,?> emailMap) {
		System.out.println("In Email Controller For Update Password -----------------------");
		HashMap<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("email", emailMap.get("email"));
		System.out.println((String) emailMap.get("email"));
		tempMap.put("url", emailMap.get("url"));
		
		
		Profile whyGod = new Profile();
		whyGod.setEmail((String) emailMap.get("email"));
		Profile profile = pserv.getProfileByEmail(whyGod);
		System.out.println(profile);
		tempMap.put("profile", profile);
		
		try {
			eserv.sendPasswordResetMessage((String) emailMap.get("email"), "Verify", tempMap);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
		
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			
		}
		return false;
	}
	
	@PostMapping("/email/verify/password")
	@NoAuthIn
	public Profile updatePassword(@RequestBody Map<String,String> pmap) {
		Profile prof = new Profile();
		prof.setEmail(pmap.get("email"));
		prof.setPasskey(pmap.get("password"));
		prof.setVerification(true);
		
		Profile p = pserv.updateProfile(prof);
		return p;
	}
	
}
