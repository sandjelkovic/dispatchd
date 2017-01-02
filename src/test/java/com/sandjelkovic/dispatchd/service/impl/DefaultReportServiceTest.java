package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.ApplicationConfiguration;
import com.sandjelkovic.dispatchd.configuration.SecurityConfiguration;
import com.sandjelkovic.dispatchd.configuration.TraktConfiguration;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DispatchdApplication.class, ApplicationConfiguration.class,
		SecurityConfiguration.class, TraktConfiguration.class})
@WebAppConfiguration
public class DefaultReportServiceTest {

	@Autowired
	private ReportTemplateRepository reportTemplateRepository;

	private ReportTemplate t;

	private ReportTemplate tFuture;

	@Before
	public void setUp() throws Exception {
		setUpT();
		reportTemplateRepository.save(t);
	}

	private void setUpT() {
		t = new ReportTemplate();
		t.setActive(true);
		t.setName("First template");
		t.setDescription("Description");
		t.setTimeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1));
		t.setTimeToGenerateReport(ZonedDateTime.now().minusHours(2));
		t.setRepeatDayOfMonth(1);
		t.setRepeatDayOfWeek(DayOfWeek.FRIDAY);
		t.setRepeatType(ReportRepeatType.WEEKLY);
		t.setRepeatInterval(ChronoUnit.WEEKS);
		t.setTimeOfDayToDeliver(LocalTime.NOON);
		reportTemplateRepository.save(t);
	}

	private void setUpTFuture() {
		tFuture = new ReportTemplate();
		tFuture.setActive(true);
		tFuture.setName("Second template");
		tFuture.setDescription("Description Future");
		tFuture.setTimeOfLastGeneratedReport(ZonedDateTime.now().minusWeeks(1));
		tFuture.setTimeToGenerateReport(ZonedDateTime.now().plusHours(2));
		tFuture.setRepeatDayOfMonth(1);
		tFuture.setRepeatDayOfWeek(DayOfWeek.SUNDAY);
		tFuture.setRepeatType(ReportRepeatType.WEEKLY);
		tFuture.setRepeatInterval(ChronoUnit.WEEKS);
		tFuture.setTimeOfDayToDeliver(LocalTime.NOON);
		reportTemplateRepository.save(t);
	}

	@After
	public void tearDown() throws Exception {
		reportTemplateRepository.delete(t);
	}

	@Test
	public void test() {

	}
}
