package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.User;

import java.time.Duration;
import java.util.Optional;

public interface UserService {
	Optional<User> findByUsername(String username);

	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

	void followTvShowForUser(User user, TvShow tvShow);

	void followTvShowForUser(User user, TvShow tvShow, Duration delay);

	void unfollowTvShowForUser(User user, TvShow tvShow);

	void enableNotificationsFor(User user, TvShow tvShow, Duration delay);

	void disableNotificationsFor(User user, TvShow tvShow);

	void generateNotifications(User user, TvShow tvShow);

	User save(User user);

	void disableUser(String username);

	void enableUser(String username);

	void approveUser(String username);

	UserService encodePassword(User user);
}
