package com.sandjelkovic.dispatchd.domain.data.repository;

import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.data.entity.UserEpisodeNotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface UserEpisodeNotificationEventRepository extends JpaRepository<UserEpisodeNotificationEvent, Long> {
	List<UserEpisodeNotificationEvent> findByNotifiedTrueOrderByNotifyTimeDesc();

	List<UserEpisodeNotificationEvent> findByNotifiedFalseAndNotifyTimeBeforeOrderByNotifyTime(Timestamp notifyTime);

	List<UserEpisodeNotificationEvent> findByEpisode(Episode episode);

	List<UserEpisodeNotificationEvent> findByEpisodeAndUser(Episode episode, User user);
}
