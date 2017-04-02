package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.gateway.api.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
public interface UserFacade {
	Optional<User> findUser(String username);

	void disableUser(String username);

	void disableUser(UserDTO user);

	void enableUser(String username);

	void enableUser(UserDTO user);

	void followTvShow(UserDTO user, TvShowDTO tvShow);

	void followTvShow(UserDTO user, TvShowDTO tvShow, Duration delay);

	void unfollowTvShow(UserDTO user, TvShowDTO tvShow);

	void enableNotificationsFor(UserDTO userDTO, TvShowDTO tvShowDTO, Duration delay);

	void disableNotificationsFor(UserDTO userDTO, TvShowDTO tvShowDTO);

}

