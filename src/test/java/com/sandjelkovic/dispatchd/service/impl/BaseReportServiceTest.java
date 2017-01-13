package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.GeneratedReportRepository;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.testutils.exceptions.DataNotSetupException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ${sandjelkovic}
 * @date 13.1.17.
 */
public class BaseReportServiceTest {

	public static final String USER_NAME = "user";
	public static final String USER_PASSWORD = "password";

	public static final String USER_TWO_NAME = "userTwo";
	public static final String USER_TWO_PASSWORD = "password";

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ReportTemplateRepository reportTemplateRepository;

	@Autowired
	protected GeneratedReportRepository generatedReportRepository;

	protected void setUpData() {
		setUpDefaultTemplates();
	}

	protected void setUpDefaultTemplates() {
		reportTemplateRepository.save(generateTemplateWithGenerationInPastWithoutShows());
		reportTemplateRepository.save(generateTemplateWithGenerationInFutureWithoutShows());
	}

	protected ReportTemplate generateTemplateAsNewTemplate() {
		return new ReportTemplate()
				.name("New template")
				.description("new desription")
				.active(true)
				.repeatDayOfMonth(1)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.of(15, 15))
				.repeatType(ReportRepeatType.MONTHLY)
				.repeatInterval(ChronoUnit.MONTHS);
	}

	protected ReportTemplate setRepeatMonthly(ReportTemplate template) {
		return template.repeatType(ReportRepeatType.MONTHLY)
				.repeatInterval(ChronoUnit.MONTHS)
				.repeatDayOfMonth(ZonedDateTime.now().getDayOfMonth());
	}

	protected ReportTemplate setRepeatWeekly(ReportTemplate template) {
		return template.repeatType(ReportRepeatType.WEEKLY)
				.repeatInterval(ChronoUnit.WEEKS)
				.repeatDayOfWeek(ZonedDateTime.now().getDayOfWeek());
	}

	protected List<ReportTemplate> generateTemplatesWithTimesToGenerate(ZonedDateTime... times) {
		List<ReportTemplate> templates = new ArrayList<>();
		Stream.of(times)
				.forEach(zonedDateTime -> templates.add(generateTemplateWithGenerationInPastWithoutShows()
						.timeToGenerateReport(zonedDateTime)));
		return templates;
	}

	protected ReportTemplate generateTemplateWithGenerationInPastWithoutShows() {
		return new ReportTemplate()
				.active(true)
				.name("First template")
				.description("Description")
				.timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1))
				.timeToGenerateReport(ZonedDateTime.now().minusHours(2))
				.repeatDayOfMonth(1)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatInterval(ChronoUnit.WEEKS)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	protected ReportTemplate generateTemplateWithGenerationInFutureWithoutShows() {
		return new ReportTemplate()
				.active(true)
				.name("Second template")
				.description("Description Future")
				.timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1))
				.timeToGenerateReport(ZonedDateTime.now().plusHours(2))
				.repeatDayOfMonth(1)
				.repeatDayOfWeek(DayOfWeek.SUNDAY)
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatInterval(ChronoUnit.WEEKS)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	protected List<ReportTemplate> generateTemplates(long count) {
		ArrayList<ReportTemplate> reportTemplates = new ArrayList<>();
		while (count-- > 0) {
			reportTemplates.add(generateTemplateWithGenerationInFutureWithoutShows());
		}
		return reportTemplates;
	}

	protected Pageable generateSimplePageable(int pageNumber, int pageSize, Sort sort) {
		return new Pageable() {
			@Override
			public int getPageNumber() {
				return pageNumber;
			}

			@Override
			public int getPageSize() {
				return pageSize;
			}

			@Override
			public int getOffset() {
				return 0;
			}

			@Override
			public Sort getSort() {
				return sort;
			}

			@Override
			public Pageable next() {
				return null;
			}

			@Override
			public Pageable previousOrFirst() {
				return null;
			}

			@Override
			public Pageable first() {
				return null;
			}

			@Override
			public boolean hasPrevious() {
				return false;
			}
		};
	}

	protected User getUser(String userName) {
		return userRepository.findOneByUsername(userName).orElseThrow(DataNotSetupException::new);
	}
}
