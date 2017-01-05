package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.repositories.GeneratedReportRepository;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.service.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.sandjelkovic.dispatchd.configuration.Constants.SPRING_PROFILE_TESTING;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {SPRING_PROFILE_TESTING})
@SpringBootTest(classes = {DispatchdApplication.class})
@Transactional
public class ReportGeneratorIntegrationTest {

	@Autowired
	private ReportTemplateRepository reportTemplateRepository;
	@Autowired
	private GeneratedReportRepository generatedReportRepository;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportGenerator target;

	@Before
	public void setUp() throws Exception {
		reportTemplateRepository.save(generateTemplatesWithTimesToGenerate(
				ZonedDateTime.now().minusMinutes(55),
				ZonedDateTime.now().minusMinutes(2),
				ZonedDateTime.now().minusSeconds(5),
				ZonedDateTime.now(),
				ZonedDateTime.now().plusSeconds(5),
				ZonedDateTime.now().plusMinutes(1),
				ZonedDateTime.now().plusMinutes(7),
				ZonedDateTime.now().plusMinutes(64),
				ZonedDateTime.now().plusHours(2)));
	}

	@Test
	public void generateReportFromTemplate() throws Exception {
		ReportTemplate reportTemplate = generateOneTemplateWithTimeToGenerate(ZonedDateTime.now());
		reportTemplate = reportTemplateRepository.save(reportTemplate);
		ZonedDateTime newGenerationTimeForTemplate = reportService.getNewGenerationTimeForTemplate(reportTemplate);

		GeneratedReport generatedReport = target.generateReportFromTemplate(reportTemplate);

		GeneratedReport retrievedReport = generatedReportRepository.findOne(generatedReport.getId());
		assertThat(retrievedReport, notNullValue());
		assertThat(retrievedReport.getId(), notNullValue());
		assertThat(retrievedReport.getReportTemplate(), notNullValue());
		assertThat(retrievedReport.getReportTemplate().getId(), equalTo(reportTemplate.getId()));
		assertThat(retrievedReport.getGeneratedReportContents().size(), is(0));

		ZonedDateTime timeOfLastGeneratedReport = retrievedReport.getReportTemplate().getTimeOfLastGeneratedReport();
		ZonedDateTime timeToGenerateReport = retrievedReport.getReportTemplate().getTimeToGenerateReport();
		assertThat(timeOfLastGeneratedReport.isBefore(ZonedDateTime.now()), is(true));
		assertThat(timeOfLastGeneratedReport.isAfter(ZonedDateTime.now().minusSeconds(5)), is(true));
		assertThat(timeToGenerateReport.isAfter(ZonedDateTime.now()), is(true));
		assertThat(timeToGenerateReport.isBefore(newGenerationTimeForTemplate.plusSeconds(5)), is(true));
	}

	private List<ReportTemplate> generateTemplatesWithTimesToGenerate(ZonedDateTime... times) {
		List<ReportTemplate> templates = new ArrayList<>();
		Stream.of(times)
				.forEach(zonedDateTime -> templates.add(generateOneTemplateWithTimeToGenerate(zonedDateTime)));
		return templates;
	}

	private ReportTemplate generateOneTemplateWithTimeToGenerate(ZonedDateTime zonedDateTime) {
		return createTemplateStub(null)
				.timeToGenerateReport(zonedDateTime);
	}

	private ReportTemplate createTemplateStub(Long id) {
		return new ReportTemplate().
				id(id).
				active(true).
				name("Template stub").
				description("Description of a stub").
				timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1)).
				timeToGenerateReport(ZonedDateTime.now().minusHours(2)).
				repeatDayOfMonth(1).
				repeatDayOfWeek(DayOfWeek.FRIDAY).
				repeatType(ReportRepeatType.WEEKLY).
				repeatInterval(ChronoUnit.WEEKS).
				timeOfDayToDeliver(LocalTime.NOON.plusMinutes(97)).
				timeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1)).
				timeToGenerateReport(ZonedDateTime.now());
	}
}
