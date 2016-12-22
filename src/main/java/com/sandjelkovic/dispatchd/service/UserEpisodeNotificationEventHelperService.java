package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.UserFollowingTvShow;

public interface UserEpisodeNotificationEventHelperService {
	void refreshEventTimes(UserFollowingTvShow following);

	void deleteNotifications(UserFollowingTvShow following);

	void generateFutureNotification(UserFollowingTvShow following);
}
