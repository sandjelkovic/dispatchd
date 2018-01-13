package com.sandjelkovic.dispatchd.event.listener;

import com.sandjelkovic.dispatchd.event.DispatchNotificationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

	private Logger log = LoggerFactory.getLogger(NotificationEventListener.class);

	@EventListener
	public void dispatchNotification(DispatchNotificationEvent event) {
		//send mail
		log.debug("Sending mail from " + event);
		//save updated version to db or deleteById
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}
		log.debug("Updating to db " + event);
	}
}
