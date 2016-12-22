package com.sandjelkovic.dispatchd.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({"classpath:/config/application-mail.properties", "classpath:/config/application-mailcredentials.properties"})
public class EmailConfiguration {
	@Value("${spring.mail.username}")
	private String sourceEmail;

	public String getSourceEmail() {
		return sourceEmail;
	}
}
