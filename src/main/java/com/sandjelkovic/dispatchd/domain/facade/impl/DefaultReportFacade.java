package com.sandjelkovic.dispatchd.domain.facade.impl;

import com.sandjelkovic.dispatchd.configuration.ValuesConfiguration;
import com.sandjelkovic.dispatchd.domain.data.TimeGenerator;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.data.repository.GeneratedReportRepository;
import com.sandjelkovic.dispatchd.domain.data.repository.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.domain.data.repository.UserRepository;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import com.sandjelkovic.dispatchd.exception.ConstraintException;
import com.sandjelkovic.dispatchd.exception.ReportsMaxContentCountReachedException;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DefaultReportFacade implements ReportFacade {

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
	public ReportTemplate saveNoUserContext(ReportTemplate reportTemplate) {
		return this.save(reportTemplate);
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

	@Override
	public Optional<ReportTemplate> findTemplate(Long id) {
		return Optional.ofNullable(reportTemplateRepository.findOne(id));
	}

	@Override
	public void delete(GeneratedReport generatedReport) {
		if (generatedReportRepository.findOne(generatedReport.getId()) == null) {
			throw new ResourceNotFoundException();
		}
		generatedReportRepository.delete(generatedReport);
	}

	@Override
	public void delete(ReportTemplate reportTemplate) {
		if (reportTemplateRepository.findOne(reportTemplate.getId()) == null) {
			throw new ResourceNotFoundException();
		}
		reportTemplateRepository.delete(reportTemplate);
	}

	@Override
	public void deleteTemplate(Long id) {
		if (reportTemplateRepository.findOne(id) == null) {
			throw new ResourceNotFoundException();
		}
		reportTemplateRepository.delete(id);
	}

	@Override
	public void deleteGenerated(Long id) {
		if (generatedReportRepository.findOne(id) == null) {
			throw new ResourceNotFoundException();
		}
		generatedReportRepository.delete(id);
	}

	public List<ReportTemplate> findAll() {
		return reportTemplateRepository.findAll();
	}

	@Override
	public ZonedDateTime getNewGenerationTimeForTemplate(ReportTemplate reportTemplate) {
		return timeGenerator.generateNewGenerationTimeForTemplate(reportTemplate);
	}

	@Override
	public List<TvShow> findTemplateShows(Long templateId) {
		List<ReportTemplate2TvShow> reportTemplate2TvShows = findTemplate(templateId)
				.map(ReportTemplate::getReportTemplate2TvShows)
				.orElseThrow(ResourceNotFoundException::new);
		return reportTemplate2TvShows.stream()
				.sorted(Comparator.comparing(ReportTemplate2TvShow::getOrderInReport))
				.map(ReportTemplate2TvShow::getTvShow)
				.collect(Collectors.toList());
	}

	private void checkAndSaveDefaultsIfNeeded(ReportTemplate template) {
		// @Min and @Max not working for some reason. Do it manually.
		if (template.getRepeatDayOfMonth() == null || template.getRepeatDayOfMonth() < 1 || template.getRepeatDayOfMonth() > 28) {
			throw new ConstraintException();
		}
		if (template.getTimeOfLastGeneratedReport() == null) {
			template.setTimeOfLastGeneratedReport(ZonedDateTime.now());
		}
		if (template.getTimeToGenerateReport() == null) {
			template.setTimeToGenerateReport(getNewGenerationTimeForTemplate(template));
		}

	}
}
