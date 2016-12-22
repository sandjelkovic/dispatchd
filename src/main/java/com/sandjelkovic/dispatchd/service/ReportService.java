package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportService {
	List<ReportTemplate> getReportTemplatesToBeGeneratedBetween(ZonedDateTime from, ZonedDateTime until);

	@PreAuthorize("authentication.name == @defaultReportService.findGenerated(#generatedReport.id).get().reportTemplate.user.username")
	GeneratedReport save(GeneratedReport generatedReport);

	@PreAuthorize("authentication.name == @defaultReportService.findTemplate(#reportTemplate.id).get().user.username")
	ReportTemplate save(ReportTemplate reportTemplate);

	@PreAuthorize("authentication.name == #generatedReport.reportTemplate.user.username")
	Optional<GeneratedReport> findGenerated(Long id);

	@PreAuthorize("authentication.name == #generatedReport.reportTemplate.user.username")
	Optional<GeneratedReport> findPublishedGenerated(Long id);

	@PostAuthorize("returnObject._embedded.username == authentication.name")
	Optional<ReportTemplate> findTemplate(Long id);

	@PreAuthorize("authentication.name == #generatedReport.reportTemplate.user.username")
	void delete(GeneratedReport generatedReport);

	@PreAuthorize("authentication.name == #reportTemplate.user.username")
	void delete(ReportTemplate reportTemplate);

	@PreAuthorize("hasAnyRole('admin', 'root')")
	List<ReportTemplate> findAll();

	@PreAuthorize("hasAnyRole('admin', 'root')")
	Page<ReportTemplate> findAll(Pageable pageable);

	@PreAuthorize("authentication.name == #username")
	Page<ReportTemplate> findAllForCurrentUser(Pageable pageable, String username);

	ZonedDateTime getNewGenerationTimeForTemplate(ReportTemplate reportTemplate);
}
