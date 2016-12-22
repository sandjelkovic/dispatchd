package com.sandjelkovic.dispatchd.data.repositories;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.entities.UserEpisodeNotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface UserEpisodeNotificationEventRepository extends JpaRepository<UserEpisodeNotificationEvent, Long> {
	List<UserEpisodeNotificationEvent> findByNotifiedTrueOrderByNotifyTimeDesc();

	List<UserEpisodeNotificationEvent> findByNotifiedFalseAndNotifyTimeBeforeOrderByNotifyTime(Timestamp notifyTime);

	List<UserEpisodeNotificationEvent> findByEpisode(Episode episode);

	List<UserEpisodeNotificationEvent> findByEpisodeAndUser(Episode episode, User user);
}
