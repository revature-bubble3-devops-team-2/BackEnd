package com.revature.utilites;

import org.springframework.context.support.ResourceBundleMessageSource;  
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;

@org.springframework.context.annotation.Configuration
public class EmailUtil {

	public FreeMarkerConfigurer freemarkerClassLoaderConfig() {
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_27);
		TemplateLoader templateLoader = new ClassTemplateLoader(this.getClass(), "/templates");
		configuration.setTemplateLoader(templateLoader);
		FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
		freeMarkerConfigurer.setConfiguration(configuration);
		return freeMarkerConfigurer;
	}

	public ResourceBundleMessageSource emailMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mailMessages");
		return messageSource;
	}

}