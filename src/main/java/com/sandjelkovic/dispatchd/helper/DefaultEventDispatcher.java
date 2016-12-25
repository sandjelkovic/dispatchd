package com.sandjelkovic.dispatchd.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

/**
 * @author ${sandjelkovic}
 */
public class DefaultEventDispatcher implements EventDispatcher {
	private static final Logger log = LoggerFactory.getLogger(DefaultEventDispatcher.class);

	private ApplicationEventPublisher eventPublisher;

	public DefaultEventDispatcher(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}


	@Override
	public void publishEvent(Object event) {
		log.debug("Dispatching from publishEvent");
		eventPublisher.publishEvent(event);
	}

	@Override
	public void publishEventAsync(Object event) {
		log.debug("Dispatching from publishEventAsync");
		eventPublisher.publishEvent(event);
	}
}
