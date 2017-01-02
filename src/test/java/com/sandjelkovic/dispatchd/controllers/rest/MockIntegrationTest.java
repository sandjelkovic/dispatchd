package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * @author ${sandjelkovic}
 * @date 2.1.17.
 */
public class MockIntegrationTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private List<User> users;

	@Autowired
	@Qualifier(Constants.TEST_USERS_INIT_BEAN_NAME)
	private CommandLineRunner usersInitRunner;

	protected ReportTemplate getTemplateWithIdForUser(Long id, String username) {
		return new ReportTemplate()
				.id(id)
				.user(userRepository.findOneByUsername(username).orElseThrow(UserNotFoundException::new))
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	protected ReportTemplateDTO getTemplateDTOWithoutId() {
		return new ReportTemplateDTO()
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}
}
