package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.entities.UserFollowingTvShow;
import com.sandjelkovic.dispatchd.data.repositories.TvShowRepository;
import com.sandjelkovic.dispatchd.data.repositories.UserFollowingTvShowRepository;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.exception.UserDoesntFollowTvShowException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.service.UserEpisodeNotificationEventHelperService;
import com.sandjelkovic.dispatchd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Duration;
import java.util.Optional;

@Component
@Transactional
public class DefaultUserService implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TvShowRepository tvShowRepository;

	@Autowired
	private UserFollowingTvShowRepository userFollowingTvShowRepository;

	@Autowired
	private UserEpisodeNotificationEventHelperService notificationEventHelperService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findOneByUsername(username);
	}

	@Override
	public Optional<User> findById(Long id) {
		return Optional.ofNullable(userRepository.findOne(id));
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email).stream()
				.findFirst();
	}

	@Override
	public void followTvShowForUser(User user, TvShow tvShow) {
		Optional.ofNullable(userFollowingTvShowRepository.findOneByUserAndTvShow(user, tvShow))
				.orElse(userFollowingTvShowRepository.save(new UserFollowingTvShow(user, tvShow)));
	}

	@Override
	public void followTvShowForUser(User user, TvShow tvShow, Duration delay) {
		User userRetrieved = userRepository.findOneByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
		TvShow tvShowRetrieved = tvShowRepository.findOne(tvShow.getId());

		UserFollowingTvShow following = Optional.ofNullable(userFollowingTvShowRepository.findOneByUserAndTvShow(user, tvShow))
				.orElseGet(() -> new UserFollowingTvShow(userRetrieved, tvShowRetrieved));
		following.setUserPickedRelativeTimeToNotify(convertDurationToEntityDelay(delay));
		following.setNotify(true);

		userFollowingTvShowRepository.save(following);

		notificationEventHelperService.refreshEventTimes(following);
	}

	private BigInteger convertDurationToEntityDelay(Duration delay) {
		return BigInteger.valueOf(delay.toMinutes());
	}

	@Override
	public void unfollowTvShowForUser(User user, TvShow tvShow) {
		Optional.ofNullable(userFollowingTvShowRepository.findOneByUserAndTvShow(user, tvShow))
				.ifPresent(userFollowingTvShowRepository::delete);
		notificationEventHelperService.deleteNotifications(new UserFollowingTvShow(user, tvShow));
	}

	@Override
	public void enableNotificationsFor(User user, TvShow tvShow, Duration delay) {
		UserFollowingTvShow following = getFollowingUserTvShowOrElseThrow(user, tvShow);

		following.setUserPickedRelativeTimeToNotify(convertDurationToEntityDelay(delay));
		following.setNotify(true);

		userFollowingTvShowRepository.save(following);
		notificationEventHelperService.refreshEventTimes(following);
	}

	@Override
	public void disableNotificationsFor(User user, TvShow tvShow) {
		UserFollowingTvShow following = getFollowingUserTvShowOrElseThrow(user, tvShow);
		following.setNotify(false);
		userFollowingTvShowRepository.save(following);
		notificationEventHelperService.deleteNotifications(new UserFollowingTvShow(user, tvShow));
	}

	@Override
	public void generateNotifications(User user, TvShow tvShow) {
		UserFollowingTvShow following = getFollowingUserTvShowOrElseThrow(user, tvShow);
		notificationEventHelperService.generateFutureNotification(following);
	}

	private UserFollowingTvShow getFollowingUserTvShowOrElseThrow(User user, TvShow tvShow) {
		return Optional.ofNullable(userFollowingTvShowRepository.findOneByUserAndTvShow(user, tvShow))
				.orElseThrow(UserDoesntFollowTvShowException::new);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void disableUser(String username) {
		userRepository.setEnabledFieldForUsername(username, false);
	}

	@Override
	public void enableUser(String username) {
		userRepository.setEnabledFieldForUsername(username, true);
	}

	@Override
	public void approveUser(String username) {
	}

	@Override
	public UserService encodePassword(User user) {
		String newPass = passwordEncoder.encode(user.getPassw());
		user.setPassw(newPass);
		return this;
	}
}
