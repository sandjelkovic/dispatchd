package com.sandjelkovic.dispatchd.event.listener;

import com.sandjelkovic.dispatchd.event.GeneratedReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderEventListener {

	private Logger log = LoggerFactory.getLogger(EmailSenderEventListener.class);
//
//	@Autowired
//	private MailSender mailSender;

	@EventListener
	@Async
	public void sendReportEmail(GeneratedReportEvent event) {
		//send mail
		log.debug("Sending mail from " + event);

		try {
			Thread.sleep(100);
		} catch (InterruptedException ignored) {
		}
	}
}
