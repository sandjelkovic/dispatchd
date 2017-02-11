package com.sandjelkovic.dispatchd.daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ${sandjelkovic}
 * @date 28.1.17.
 */
@Component
public class ContentRefreshDaemon {
	private static final Logger log = LoggerFactory.getLogger(ContentRefreshDaemon.class);

	private volatile boolean inProgress = false;

	@Async
	@Scheduled(fixedDelayString = "#{${content.refresh.interval.minutes}*60*60}")
	public void generateReports() {
		if (!inProgress) {
			inProgress = true;
			try {

			} catch (RuntimeException e) {
				throw e;
			} finally {
				inProgress = false;
			}
		}
	}

}
