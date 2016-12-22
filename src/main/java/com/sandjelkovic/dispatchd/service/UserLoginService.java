package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class UserLoginService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userService.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException("User with username " + username + " doesn't exist.");
		}
		return mapUserToSpringUser(user.get());
	}

	private UserDetails mapUserToSpringUser(User user) {
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(SimpleGrantedAuthority::new)
				.collect(toList());
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassw(), user.isEnabled(),
				accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
	}
}
