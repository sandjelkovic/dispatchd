package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.api.dto.UserDTO;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.service.UserService;
import com.sandjelkovic.dispatchd.exception.IncompleteObjectForOperationException;
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
	public void disableUser(UserDTO user) {
		this.disableUser(user.getUsername());
	}

	@Override
	public void enableUser(String username) {
		userService.enableUser(username);
	}

	@Override
	public void enableUser(UserDTO user) {
		this.enableUser(user.getUsername());
	}

	@Override
	public void followTvShow(UserDTO userDTO, TvShowDTO tvShowDTO) {
		User user = conversionService.convert(userDTO, User.class);
		TvShow tvShow = conversionService.convert(tvShowDTO, TvShow.class);
		checkForExistingIds(user, tvShow);
		userService.followTvShowForUser(user, tvShow);
	}

	@Override
	public void unfollowTvShow(UserDTO userDTO, TvShowDTO tvShowDTO) {
		User user = conversionService.convert(userDTO, User.class);
		TvShow tvShow = conversionService.convert(tvShowDTO, TvShow.class);
		checkForExistingIds(user, tvShow);
		userService.unfollowTvShowForUser(user, tvShow);
	}

	@Override
	public void enableNotificationsFor(UserDTO userDTO, TvShowDTO tvShowDTO, Duration delay) {
		User user = conversionService.convert(userDTO, User.class);
		TvShow tvShow = conversionService.convert(tvShowDTO, TvShow.class);
		checkForExistingIds(user, tvShow);

		userService.enableNotificationsFor(user, tvShow, delay);
		userService.generateNotifications(user, tvShow);
	}

	@Override
	public void disableNotificationsFor(UserDTO userDTO, TvShowDTO tvShowDTO) {
		User user = conversionService.convert(userDTO, User.class);
		TvShow tvShow = conversionService.convert(tvShowDTO, TvShow.class);
		checkForExistingIds(user, tvShow);

		userService.disableNotificationsFor(user, tvShow);
	}

	@Override
	public void followTvShow(UserDTO userDTO, TvShowDTO tvShowDTO, Duration delay) {
		User user = conversionService.convert(userDTO, User.class);
		TvShow tvShow = conversionService.convert(tvShowDTO, TvShow.class);
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
