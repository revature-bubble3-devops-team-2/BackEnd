package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.dto.ProfileDTO;
import com.revature.models.Profile;
import com.revature.services.EmailService;
import com.revature.services.ProfileServiceImpl;

import freemarker.template.TemplateException;

@ExtendWith(MockitoExtension.class)
class EmailControllerTest {

	@InjectMocks
	EmailController emailController;

	@Mock
	EmailService emailService;

	@Mock
	ProfileServiceImpl profileService;

	private static Profile profile;
	private static Map<String, Object> map;
	private static final String VERIFY = "Verify";
	private static final String EMAIL = "email";

	@BeforeEach
	void init() {
		profile = new Profile();
		profile.setEmail(EMAIL);
		map = new HashMap<>();
		map.put("profile", profile);
		map.put("email", null);
		map.put("url", null);
	}

	@Test
	void testValidSendEmail() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doNothing().when(emailService).sendVerificationMessage(null, VERIFY, map);
		assertTrue(emailController.sendEmail(map));
	}

	@Test
	void testSendEmailIOException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new IOException()).when(emailService).sendVerificationMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmail(map));
	}

	@Test
	void testSendEmailTemplateException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new TemplateException(null)).when(emailService).sendVerificationMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmail(map));
	}

	@Test
	void testSendEmailMessagingException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new MessagingException()).when(emailService).sendVerificationMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmail(map));
	}

	@Test
	void testEmailVerified() {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		when(profileService.updateProfile(profile)).thenReturn(profile);
		assertTrue(emailController.emailVerified(EMAIL));
	}

	@Test
	void testEmailNotVerified() {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		when(profileService.updateProfile(profile)).thenReturn(null);
		assertFalse(emailController.emailVerified(EMAIL));
	}

	@Test
	void testValidSendEmailForUpdatePassword() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doNothing().when(emailService).sendPasswordResetMessage(null, VERIFY, map);
		assertTrue(emailController.sendEmailForUpdatePassword(map));
	}

	@Test
	void testSendEmailForUpdatePasswordIOException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new IOException()).when(emailService).sendPasswordResetMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmailForUpdatePassword(map));
	}

	@Test
	void testSendEmailForUpdatePasswordTemplateException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new TemplateException(null)).when(emailService).sendPasswordResetMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmailForUpdatePassword(map));
	}

	@Test
	void testSendEmailForUpdatePasswordMessagingException() throws IOException, TemplateException, MessagingException {
		when(profileService.getProfileByEmail(any(Profile.class))).thenReturn(profile);
		doThrow(new MessagingException()).when(emailService).sendPasswordResetMessage(null, VERIFY, map);
		assertFalse(emailController.sendEmailForUpdatePassword(map));
	}

	@Test
	void testUpdatePassword() {
		when(profileService.updatePassword(any(Profile.class))).thenReturn(profile);
		ResponseEntity<ProfileDTO> response = emailController.updatePassword(new HashMap<>());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
