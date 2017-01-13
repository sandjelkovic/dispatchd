package com.sandjelkovic.dispatchd.config;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@Profile(Constants.SPRING_PROFILE_TESTING)
@Order(105)
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void configure(AuthenticationManagerBuilder auth) {
//		try {
//			auth
//					.inMemoryAuthentication()
//					.withUser("user").password("password").authorities(Constants.DEFAULT_USER_ROLES).disabled(false).accountExpired(false).accountLocked(false).credentialsExpired(false).and()
//					.withUser("admin").password("password").roles("USER", "ADMIN");
//		} catch (Exception e) {
//		}
	}

	@Bean(Constants.TEST_USERS_INIT_BEAN_NAME)
	@Profile(Constants.SPRING_PROFILE_TESTING)
	public CommandLineRunner commandLineRunner(PasswordEncoder passwordEncoder,
	                                           List<User> users) {
		return args -> {
			users.forEach(user -> {
				userRepository.findOneByUsername(user.getUsername())
						.ifPresent(u -> user.setId(u.getId()));
				user.setPassw(passwordEncoder.encode(user.getPassw()));
				userRepository.saveAndFlush(user);
			});
		};
	}

	@Bean("defaultUser")
	@Scope(scopeName = "prototype")
	public User defaultUser() {
		return new User()
				.username("user")
				.approved(true)
				.enabled(true)
				.passw("password")
				.email("user@example.com")
				.authorities(Stream.of(Constants.DEFAULT_USER_ROLES)
						.collect(Collectors.toSet()));
	}

	@Bean("defaultUserTwo")
	@Scope(scopeName = "prototype")
	public User defaultUserTwo() {
		return new User()
				.username("userTwo")
				.approved(true)
				.enabled(true)
				.passw("password")
				.email("userTwo@example.com")
				.authorities(Stream.of(Constants.DEFAULT_USER_ROLES)
						.collect(Collectors.toSet()));
	}

	@Bean("adminUser")
	@Scope(scopeName = "prototype")
	public User adminUser() {
		return new User()
				.username("admin")
				.approved(true)
				.enabled(true)
				.passw("password")
				.email("admin@example.com")
				.authorities(Stream.of(Constants.ADMIN_ROLES)
						.collect(Collectors.toSet()));
	}
}
