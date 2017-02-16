package com.sandjelkovic.dispatchd.helpers;

import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportRepeatType;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.User;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ${sandjelkovic}
 * @date 2.1.17.
 */
public class TestDataGenerator {

	public ReportTemplate getTemplateWithIdForUser(Long id, User user) {
		return new ReportTemplate()
				.id(id)
				.user(user)
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	public ReportTemplateDTO getTemplateDTOWithoutId() {
		return new ReportTemplateDTO()
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	public User createUser(String username, String password) {
		return new User()
				.username(username)
				.approved(true)
				.enabled(true)
				.passw(password)
				.email(username + "@example.com")
				.authorities(Stream.of(Constants.DEFAULT_USER_ROLES)
						.collect(Collectors.toSet()));
	}
}
