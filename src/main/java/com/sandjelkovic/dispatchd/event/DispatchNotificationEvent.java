package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.data.entities.UserEpisodeNotificationEvent;

public class DispatchNotificationEvent {
	private UserEpisodeNotificationEvent eventInfo;

	public DispatchNotificationEvent(UserEpisodeNotificationEvent data) {
		this.eventInfo = data;
	}
}
