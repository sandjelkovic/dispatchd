package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.gateway.api.dto.EpisodeNotificationDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface EpisodeNotificationFacade {
	List<EpisodeNotificationDTO> findUnreadNotifications();

	List<EpisodeNotificationDTO> findUnreadNotifications(Instant endTime);

	List<EpisodeNotificationDTO> findUnreadNotifications(Instant beginTime, Instant endTime);
}
