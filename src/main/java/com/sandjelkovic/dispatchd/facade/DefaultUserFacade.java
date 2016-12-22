package com.sandjelkovic.dispatchd.facade;

import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.dto.UserDto;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.exception.IncompleteObjectForOperationException;
import com.sandjelkovic.dispatchd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
public class DefaultUserFacade implements UserFacade {

	@Autowired
	private UserService userService;

	@Autowired
	private ConversionService conversionService;

	@Override
	public Optional<User> findUser(String username) {
		return userService.findByUsername(username);
	}

	@Override
	public void disableUser(String username) {
		userService.disableUser(username);
	}

	@Override
	public void disableUser(UserDto user) {
		this.disableUser(user.getUsername());
	}

	@Override
	public void enableUser(String username) {
		userService.enableUser(username);
	}

	@Override
	public void enableUser(UserDto user) {
		this.enableUser(user.getUsername());
	}

	@Override
	public void followTvShow(UserDto userDto, TvShowDto tvShowDto) {
		User user = conversionService.convert(userDto, User.class);
		TvShow tvShow = conversionService.convert(tvShowDto, TvShow.class);
		checkForExistingIds(user, tvShow);
		userService.followTvShowForUser(user, tvShow);
	}

	@Override
	public void unfollowTvShow(UserDto userDto, TvShowDto tvShowDto) {
		User user = conversionService.convert(userDto, User.class);
		TvShow tvShow = conversionService.convert(tvShowDto, TvShow.class);
		checkForExistingIds(user, tvShow);
		userService.unfollowTvShowForUser(user, tvShow);
	}

	@Override
	public void enableNotificationsFor(UserDto userDto, TvShowDto tvShowDto, Duration delay) {
		User user = conversionService.convert(userDto, User.class);
		TvShow tvShow = conversionService.convert(tvShowDto, TvShow.class);
		checkForExistingIds(user, tvShow);

		userService.enableNotificationsFor(user, tvShow, delay);
		userService.generateNotifications(user, tvShow);
	}

	@Override
	public void disableNotificationsFor(UserDto userDto, TvShowDto tvShowDto) {
		User user = conversionService.convert(userDto, User.class);
		TvShow tvShow = conversionService.convert(tvShowDto, TvShow.class);
		checkForExistingIds(user, tvShow);

		userService.disableNotificationsFor(user, tvShow);
	}

	@Override
	public void followTvShow(UserDto userDto, TvShowDto tvShowDto, Duration delay) {
		User user = conversionService.convert(userDto, User.class);
		TvShow tvShow = conversionService.convert(tvShowDto, TvShow.class);
		checkForExistingIds(user, tvShow);
		userService.followTvShowForUser(user, tvShow, delay);
		userService.generateNotifications(user, tvShow);
	}

	private void checkForExistingIds(User user, TvShow tvShow) {
		if (user.getId() == null) {
			throw new IncompleteObjectForOperationException(user);
		}
		if (tvShow.getId() == null) {
			throw new IncompleteObjectForOperationException(tvShow);
		}
	}

}
