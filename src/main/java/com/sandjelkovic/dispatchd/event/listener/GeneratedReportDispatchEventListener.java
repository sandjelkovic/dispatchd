package com.sandjelkovic.dispatchd.event.listener;

import com.sandjelkovic.dispatchd.event.GeneratedReportEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GeneratedReportDispatchEventListener {

	private Logger log = LoggerFactory.getLogger(GeneratedReportDispatchEventListener.class);

	@EventListener
	public void handleGeneratedReport(GeneratedReportEvent event) {
		log.debug("Handling generated report from the event listener: " + event.eventInfo);
	}
}
