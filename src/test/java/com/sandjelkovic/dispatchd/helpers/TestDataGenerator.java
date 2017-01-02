package com.sandjelkovic.dispatchd.helpers;

import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
}
