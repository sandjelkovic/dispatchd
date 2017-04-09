package com.sandjelkovic.dispatchd.configuration;


import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class DevDatabaseInitialisator {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private List<User> users;


	@Bean
	@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
	public CommandLineRunner devUsersRefresher(PasswordEncoder passwordEncoder,
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

	/*private void exampleSendMailMethod() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("example@gmail.com");
		message.setSubject("Dev instance is starting up");
		message.setTo("example2@gmail.com");
		message.setText("Dispatchd dev instance is starting up. This is just an email test. Please go to <a href=\"localhost:8080\">here</a> to test it out.");
		System.err.println("Sending " + message);

		try{
			mailSender.send(message);
		} catch(MailException e) {
			System.err.println(e);
		} catch(Exception e) {
			System.err.println(e);
		}
		System.err.println("Should be sent...");
	}*/

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

	@Bean("rootUser")
	@Scope(scopeName = "prototype")
	public User rootUser() {
		return new User()
				.username("root")
				.approved(true)
				.enabled(true)
				.passw("password")
				.email("root@example.com")
				.authorities(Stream.of(Constants.ADMIN_ROLES)
						.collect(Collectors.toSet()));
	}
}
