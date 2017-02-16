package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.configuration.EmailConfiguration;
import com.sandjelkovic.dispatchd.domain.data.entity.UserEpisodeNotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class NotificationEvent2EmailMessageConverter implements Converter<UserEpisodeNotificationEvent, SimpleMailMessage> {

	@Autowired
	private EmailConfiguration emailConfiguration;

	@Override
	public SimpleMailMessage convert(UserEpisodeNotificationEvent source) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailConfiguration.getSourceEmail());
		message.setTo(source.getUser().getEmail());
		message.setSubject(source.getTitle());
		message.setSentDate(Date.from(Instant.now()));
		message.setText(source.getDescription());
		return message;
	}
}
