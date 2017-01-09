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

	@PreAuthorize("#generatedReport.id == null or authentication.name == @defaultReportService.findGenerated(#generatedReport.id).get().reportTemplate.user.username")
	GeneratedReport save(GeneratedReport generatedReport);

	@PreAuthorize("#reportTemplate.id == null or authentication.name == @defaultReportService.findTemplate(#reportTemplate.id).get().user.username")
	ReportTemplate save(ReportTemplate reportTemplate);

	@PostAuthorize("returnObject.present and returnObject.get().reportTemplate.user.username == authentication.name")
	Optional<GeneratedReport> findGenerated(Long id);

	@PostAuthorize("returnObject.present and returnObject.get().user.username == authentication.name")
	Optional<ReportTemplate> findTemplate(Long id);

	void delete(GeneratedReport generatedReport);

	void delete(ReportTemplate reportTemplate);

	void deleteTemplate(Long id);

	void deleteGenerated(Long id);

	@PreAuthorize("hasAnyRole('admin', 'root')")
	List<ReportTemplate> findAll();

	@PreAuthorize("hasAnyRole('admin', 'root')")
	Page<ReportTemplate> findAll(Pageable pageable);

	@PreAuthorize("authentication.name == #username")
	Page<ReportTemplate> findTemplatesForUser(Pageable pageable, String username);

	@PreAuthorize("authentication.name == #username")
	Page<GeneratedReport> findGeneratedForUser(Pageable pageable, String username);

	@PreAuthorize("authentication.name == #username")
	Page<GeneratedReport> findGeneratedByTemplateForUser(Pageable pageable, Long templateId, String username);

	ZonedDateTime getNewGenerationTimeForTemplate(ReportTemplate reportTemplate);
}
