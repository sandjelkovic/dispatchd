package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.EpisodeNotificationDTO;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Component
public class DefaultEpisodeNotificationFacade implements EpisodeNotificationFacade {

	@Override
	public List<EpisodeNotificationDTO> findUnreadNotifications() {
		return findUnreadNotifications(Instant.now(), Instant.now().plus(Duration.ofHours(1)));
	}

	@Override
	public List<EpisodeNotificationDTO> findUnreadNotifications(Instant endTime) {
		return findUnreadNotifications(Instant.now(), endTime);
	}

	@Override
	public List<EpisodeNotificationDTO> findUnreadNotifications(Instant beginTime, Instant endTime) {
		return null;
	}
}
