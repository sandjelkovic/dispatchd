package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.domain.data.entity.UserEpisodeNotificationEvent;

public class DispatchNotificationEvent {
	private UserEpisodeNotificationEvent eventInfo;

	public DispatchNotificationEvent(UserEpisodeNotificationEvent data) {
		this.eventInfo = data;
	}
}
