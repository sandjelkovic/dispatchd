package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.configuration.ValuesConfiguration;
import com.sandjelkovic.dispatchd.data.TimeGenerator;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.GeneratedReportRepository;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.exception.ReportsMaxContentCountReachedException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultReportService implements ReportService {

	@Autowired
	private ValuesConfiguration valuesConfiguration;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GeneratedReportRepository generatedReportRepository;

	@Autowired
	private ReportTemplateRepository reportTemplateRepository;

	@Autowired
	private TimeGenerator timeGenerator;

	@Override
	public List<ReportTemplate> getReportTemplatesToBeGeneratedBetween(ZonedDateTime from, ZonedDateTime until) {
		List<ReportTemplate> result = new ArrayList<>();
		until = Optional.ofNullable(until)
				.orElse(ZonedDateTime.now());
		if (from == null) {
			result.addAll(reportTemplateRepository.findByActiveTrueAndTimeToGenerateReportIsBefore(until));
		} else {
			result.addAll(reportTemplateRepository.findByActiveTrueAndTimeToGenerateReportIsBetween(from, until));
		}
		return result;
	}

	@Override
	public GeneratedReport save(@Valid GeneratedReport generatedReport) {
		return generatedReportRepository.save(generatedReport);
	}

	@Override
	public ReportTemplate save(@Valid ReportTemplate reportTemplate) {
		checkAndSaveDefaultsIfNeeded(reportTemplate);
		if (reportTemplate.getReportTemplate2TvShows().size() > valuesConfiguration.getReportMaxTargetsCount()) {
			throw new ReportsMaxContentCountReachedException();
		}
		return reportTemplateRepository.save(reportTemplate);
	}

	@Override
	public Page<ReportTemplate> findAll(Pageable pageable) {
		return reportTemplateRepository.findAll(pageable);
	}

	@Override
	public Page<ReportTemplate> findTemplatesForUser(Pageable pageable, String username) {
		User user = userRepository.findOneByUsername(username).orElseThrow(UserNotFoundException::new);
		return reportTemplateRepository.findByUserId(pageable, user.getId());
	}

	@Override
	public Page<GeneratedReport> findGeneratedForUser(Pageable pageable, String username) {
		return generatedReportRepository.findByReportTemplateUserUsername(pageable, username);
	}

	@Override
	public Page<GeneratedReport> findGeneratedByTemplateForUser(Pageable pageable, Long templateId, String username) {
		Page<GeneratedReport> reportPage;
		if (templateId != null) {
			reportPage = generatedReportRepository.findByReportTemplateIdAndReportTemplateUserUsername(pageable, templateId, username);
		} else {
			reportPage = generatedReportRepository.findByReportTemplateUserUsername(pageable, username);
		}
		return reportPage;
	}

	@Override
	public Optional<GeneratedReport> findGenerated(Long id) {
		return Optional.ofNullable(generatedReportRepository.findOne(id));
	}

	public Optional<ReportTemplate> findTemplate(Long id) {
		return Optional.ofNullable(reportTemplateRepository.findOne(id));
	}

	public void delete(GeneratedReport generatedReport) {
		generatedReportRepository.delete(generatedReport);
	}

	public void delete(ReportTemplate reportTemplate) {
		reportTemplateRepository.delete(reportTemplate);
	}

	public List<ReportTemplate> findAll() {
		return reportTemplateRepository.findAll();
	}

	@Override
	public ZonedDateTime getNewGenerationTimeForTemplate(ReportTemplate reportTemplate) {
		return timeGenerator.generateNewGenerationTimeForTemplate(reportTemplate);
	}

	private void checkAndSaveDefaultsIfNeeded(ReportTemplate template) {
		// @Min and @Max not working for some reason. Do it manually.
		if (template.getRepeatDayOfMonth() == null || template.getRepeatDayOfMonth() < 1 || template.getRepeatDayOfMonth() > 28) {
			throw new ConstraintViolationException(null);
		}
		if (template.getTimeOfLastGeneratedReport() == null) {
			template.setTimeOfLastGeneratedReport(ZonedDateTime.now());
		}
		if (template.getTimeToGenerateReport() == null) {
			template.setTimeToGenerateReport(getNewGenerationTimeForTemplate(template));
		}

	}
}
