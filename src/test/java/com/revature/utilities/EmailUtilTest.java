package com.revature.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.revature.utilites.EmailUtil;

//At least one assert for each method
class EmailUtilTest {
	
	
	EmailUtil eUtil = new EmailUtil();
	
	@Test
	void testFreeMarkerConfig() {
		FreeMarkerConfigurer fmc = eUtil.freemarkerClassLoaderConfig();
		assertNotNull(fmc);
	}
	
	@Test	
	void testEmailMessageSource() {
		ResourceBundleMessageSource rbms = eUtil.emailMessageSource();
		assertEquals("[mailMessages]", rbms.getBasenameSet().toString());
	}
}
