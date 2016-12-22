package com.sandjelkovic.dispatchd.event.listener;

import com.sandjelkovic.dispatchd.event.GeneratedReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GeneratedReportEmailSenderEventListener {

	private Logger log = LoggerFactory.getLogger(GeneratedReportEmailSenderEventListener.class);

	@EventListener
	public void handleGeneratedReport(GeneratedReportEvent event) {
		//send mail
		log.debug("Sending mail from " + event);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {}
		log.debug("Updating to db " + event);
	}
}
