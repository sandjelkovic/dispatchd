package com.sandjelkovic.dispatchd.facade;

import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.dto.UserDto;
import com.sandjelkovic.dispatchd.data.entities.User;
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

	void followTvShow(UserDto user, TvShowDto tvShow);

	void followTvShow(UserDto user, TvShowDto tvShow, Duration delay);

	void unfollowTvShow(UserDto user, TvShowDto tvShow);

	void enableNotificationsFor(UserDto userDto, TvShowDto tvShowDto, Duration delay);

	void disableNotificationsFor(UserDto userDto, TvShowDto tvShowDto);

}

