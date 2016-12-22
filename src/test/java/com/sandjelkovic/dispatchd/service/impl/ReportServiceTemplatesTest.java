package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.ApplicationConfiguration;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.service.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = {DispatchdApplication.class, ApplicationConfiguration.class})
@Transactional
public class ReportServiceTemplatesTest {

	@Autowired
	private ReportTemplateRepository reportTemplateRepository;

	@Autowired
	private ReportService reportService;

	@Before
	public void setUp() throws Exception {
	}

	private ReportTemplate createTemplateStub() {
		return new ReportTemplate().
				id(null).
				active(true).
				name("First template").
				description("Description").
				timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1)).
				timeToGenerateReport(ZonedDateTime.now().minusHours(2)).
				repeatDayOfMonth(1).
				repeatDayOfWeek(DayOfWeek.FRIDAY).
				repeatType(ReportRepeatType.WEEKLY).
				repeatInterval(ChronoUnit.WEEKS).
				timeOfDayToDeliver(LocalTime.NOON).
				timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1)).
				timeToGenerateReport(ZonedDateTime.now());
	}

	private ReportTemplate createTemplateAsNew() {
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

	@Test
	public void saveExistingFilledTemplateTest() throws Exception {
		long countBefore = reportTemplateRepository.count();
		ReportTemplate beforeSaveBean = createTemplateStub();

		ReportTemplate savedBean = reportService.save(beforeSaveBean);

		assertThat(savedBean, notNullValue());
		long countAfter = reportTemplateRepository.count();
		assertThat("Should be one more a after saving", countAfter, is(countBefore + 1));

		long generatedId = savedBean.getId();
		beforeSaveBean.setId(generatedId);
		assertThat(savedBean, samePropertyValuesAs(beforeSaveBean));
		assertThat(savedBean, samePropertyValuesAs(reportTemplateRepository.findOne(generatedId)));
	}

	@Test
	public void saveNewTemplateTest() throws Exception {
		long countBefore = reportTemplateRepository.count();
		ReportTemplate beforeSaveBean = createTemplateAsNew();

		ReportTemplate savedBean = reportService.save(beforeSaveBean);

		assertThat(savedBean, notNullValue());

		long countAfter = reportTemplateRepository.count();
		assertThat("Should be one more after saving", countAfter, is(countBefore + 1));
	}

	@Test(expected = ConstraintViolationException.class)
	public void saveNewFailOnMissingTimeOfDay() {
		ReportTemplate template = createTemplateAsNew()
				.timeOfDayToDeliver(null);

		reportService.save(template);
		fail();
	}

	@Test(expected = ConstraintViolationException.class)
	public void saveNewFailOnNegativeDayOfMonth() {
		ReportTemplate template = createTemplateAsNew()
				.repeatDayOfMonth(-5);

		reportService.save(template);
		fail();
	}

	@Test
	public void getTemplatesBetweenWeeklyTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now().plusMinutes(2)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(
				ZonedDateTime.now().minusMinutes(1), ZonedDateTime.now().plusMinutes(5));

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(2));
	}

	@Test
	public void getTemplatesBetweenNoResultTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().minusHours(2)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(
				ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(5));

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(0));
	}

	@Test
	public void getTemplatesBetweenWithoutFromParamSuccessTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().minusHours(2)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(null, ZonedDateTime.now().plusMinutes(5));

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(3));
	}

	@Test
	public void getTemplatesBetweenWithoutFromParamNoResultTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().plusHours(2),
				ZonedDateTime.now().plusMinutes(15),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().plusMinutes(25)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(null, ZonedDateTime.now().plusMinutes(5));

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(0));
	}

	@Test
	public void getTemplatesBetweenInverseDatesTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().plusMinutes(1)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(
				ZonedDateTime.now().plusMinutes(5), ZonedDateTime.now().minusMinutes(5));

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(0));
	}

	@Test
	public void getTemplatesBetweenAllNullsTest() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().plusMinutes(1)));

		List<ReportTemplate> retrievedTemplates = reportService.getReportTemplatesToBeGeneratedBetween(null, null);

		assertThat(retrievedTemplates, notNullValue());
		assertThat(retrievedTemplates.size(), is(2));
	}

	private ReportTemplate setRepeatMonthly(ReportTemplate template) {
		return template.repeatType(ReportRepeatType.MONTHLY)
				.repeatInterval(ChronoUnit.MONTHS)
				.repeatDayOfMonth(ZonedDateTime.now().getDayOfMonth());
	}

	private ReportTemplate setRepeatWeekly(ReportTemplate template) {
		return template.repeatType(ReportRepeatType.WEEKLY)
				.repeatInterval(ChronoUnit.WEEKS)
				.repeatDayOfWeek(ZonedDateTime.now().getDayOfWeek());
	}

	private List<ReportTemplate> generateTemplatesWithTimesToGenerate(ZonedDateTime... times) {
		List<ReportTemplate> templates = new ArrayList<>();
		Stream.of(times)
				.forEach(zonedDateTime -> templates.add(createTemplateStub()
						.timeToGenerateReport(zonedDateTime)));
		return templates;
	}
}
