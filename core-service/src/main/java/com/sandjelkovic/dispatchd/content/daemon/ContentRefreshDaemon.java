package com.sandjelkovic.dispatchd.content.daemon;

import com.sandjelkovic.dispatchd.domain.service.ContentRefreshService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ContentRefreshService contentRefreshService;

	@Async
	@Scheduled(fixedDelayString = "#{${content.refresh.interval.minutes}*1000*60}", initialDelay = 1000 * 5)
	public void invokeContentRefresh() {
		log.debug("content refresh started");
		try {
			contentRefreshService.refreshExistingContent();
		} catch (RuntimeException e) {
			throw e;
		}
	}
}

