package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.api.dto.UserDto;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public interface UserFacade {
	Optional<User> findUser(String username);

	void disableUser(String username);

	void disableUser(UserDto user);

	void enableUser(String username);

	void enableUser(UserDto user);

	void followTvShow(UserDto user, TvShowDTO tvShow);

	void followTvShow(UserDto user, TvShowDTO tvShow, Duration delay);

	void unfollowTvShow(UserDto user, TvShowDTO tvShow);

	void enableNotificationsFor(UserDto userDto, TvShowDTO tvShowDTO, Duration delay);

	void disableNotificationsFor(UserDto userDto, TvShowDTO tvShowDTO);

}

