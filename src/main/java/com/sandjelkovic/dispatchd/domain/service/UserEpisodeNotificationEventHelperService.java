package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.UserFollowingTvShow;

public interface UserEpisodeNotificationEventHelperService {
	void refreshEventTimes(UserFollowingTvShow following);

	void deleteNotifications(UserFollowingTvShow following);

	void generateFutureNotification(UserFollowingTvShow following);
}
