package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.EpisodeNotificationDTO;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public interface EpisodeNotificationFacade {
	public List<EpisodeNotificationDTO> findUnreadNotifications();
	public List<EpisodeNotificationDTO> findUnreadNotifications(Instant endTime);
	public List<EpisodeNotificationDTO> findUnreadNotifications(Instant beginTime, Instant endTime);
}