package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.UserEpisodeNotificationEvent;
import com.sandjelkovic.dispatchd.domain.data.repository.UserEpisodeNotificationEventRepository;
import com.sandjelkovic.dispatchd.event.DispatchNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class NotificationDaemon {

	private static final Logger log = LoggerFactory.getLogger(NotificationDaemon.class);
	private final static Duration DEFAULT_INTERVAL = Duration.ofMinutes(1);

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	private volatile boolean inProgress = false;

	@Autowired
	private UserEpisodeNotificationEventRepository notificationEventRepository;

	@Autowired
	private MailSender mailSender;

	@Autowired
	@Qualifier(Constants.CONVERSION_SERVICE_BEAN_NAME)
	private ConversionService conversionService;


	@Async
	@Transactional
	@Scheduled(fixedRate = 10 * 1000, initialDelay = 10 * 100)
	public void refreshNotifications() throws InterruptedException {
		if (!inProgress) {
			inProgress = true;
			log.debug("Starting notification refresh...");
			try{
				Set<UserEpisodeNotificationEvent> events = collect();
				updateStatus(events);
				dispatchBatched(events);
			} catch (RuntimeException e) {
				throw e;
			} finally {
				inProgress = false;
			}
		} else {
			log.info("Notification collection and dispatching is already in progress. Check the server load. Skipping...");
		}
	}

	private void dispatchBatched(Set<UserEpisodeNotificationEvent> events) throws InterruptedException {
		List<SimpleMailMessage> messages = events.stream()
				.map(event -> conversionService.convert(event, SimpleMailMessage.class))
				.collect(toList());
		SimpleMailMessage[] messageArray = convertMessageListToMessageArray(messages);
		mailSender.send(messageArray);
	}

	private SimpleMailMessage[] convertMessageListToMessageArray(List<SimpleMailMessage> messages) {
		SimpleMailMessage [] array = new SimpleMailMessage[messages.size()];
		for (int i = 0; i < messages.size(); i++) {
			array[i] = messages.get(i);
		}
		return array;
	}

	private void dispatchEventDriven(Set<UserEpisodeNotificationEvent> events) throws InterruptedException {
		events.stream()
				.forEach(this::publishEvent);
	}

	private void publishEvent(UserEpisodeNotificationEvent event) {
		eventPublisher.publishEvent(new DispatchNotificationEvent(event));
	}

	private void updateStatus(Set<UserEpisodeNotificationEvent> events) {
		events.stream().forEach(event -> event.setNotified(true));
		notificationEventRepository.save(events);
	}

	public Set<UserEpisodeNotificationEvent> collect() throws InterruptedException {
		Instant upperBound = Instant.now().plus(DEFAULT_INTERVAL);
		return notificationEventRepository.findByNotifiedFalseAndNotifyTimeBeforeOrderByNotifyTime(Timestamp.from(upperBound))
				.stream()
				.collect(Collectors.toSet());
	}

}
