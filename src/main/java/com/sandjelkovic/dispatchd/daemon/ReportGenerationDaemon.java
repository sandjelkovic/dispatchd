package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.domain.service.ReportGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportGenerationDaemon {
	private static final Logger log = LoggerFactory.getLogger(NotificationDaemon.class);

	private volatile boolean inProgress = false;

	@Autowired
	private ReportGeneratorService reportGeneratorService;

	@Async
	@Scheduled(fixedRate = 10 * 1000, initialDelay = 10 * 100)
	public void generateReports() {
		if (!inProgress) {
			inProgress = true;
			log.debug("Starting report generation...");
			try {
				reportGeneratorService.generateReports();
			} catch (RuntimeException e) {
				throw e;
			} finally {
				inProgress = false;
			}
		} else {
			log.info("Report generation is already in progress. Check the server load. Skipping...");
		}
	}
}
