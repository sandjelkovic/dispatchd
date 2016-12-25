package com.sandjelkovic.dispatchd.helper;

import org.springframework.scheduling.annotation.Async;

/**
 * @author ${sandjelkovic}
 */
public interface EventDispatcher {
	void publishEvent(Object event);

	/**
	 * Publishes the received event in a separate thread using Spring's {@link Async}.
	 * Note that the event listeners can also be Async and that would create a separate task in addition the the one that this method is using
	 *
	 * @param event
	 */
	@Async
	void publishEventAsync(Object event);
}
