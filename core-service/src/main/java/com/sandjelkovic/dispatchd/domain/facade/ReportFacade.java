package com.sandjelkovic.dispatchd.domain.facade;

import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportFacade {
	List<ReportTemplate> getReportTemplatesToBeGeneratedBetween(ZonedDateTime from, ZonedDateTime until);

	// should not be ever possible for a user to update the generatedReport.
	// Maybe in the future if a need arises, but for now, security is not needed
	GeneratedReport save(GeneratedReport generatedReport);

	@PreAuthorize("#reportTemplate.id == null or authentication.name == @defaultReportFacade.findTemplate(#reportTemplate.id).get().user.username")
	ReportTemplate save(ReportTemplate reportTemplate);

	// no user Context for updating while generating new reports.
	ReportTemplate saveNoUserContext(ReportTemplate reportTemplate);

	@PostAuthorize("!returnObject.present or (returnObject.present and returnObject.get().reportTemplate.user.username == authentication.name)")
	Optional<GeneratedReport> findGenerated(Long id);

	@PostAuthorize("!returnObject.present or (returnObject.present and returnObject.get().user.username == authentication.name)")
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

	@PreAuthorize("authentication.name == @defaultReportFacade.findTemplate(#templateId).get().user.username")
	List<TvShow> findTemplateShows(Long templateId);

	@PreAuthorize("authentication.name == @defaultReportFacade.findTemplate(#templateId).get().user.username")
	void disconnectAllShows(Long templateId);

	@PreAuthorize("authentication.name == @defaultReportFacade.findTemplate(#templateId).get().user.username")
	void disconnectShow(Long templateId, String showId);

	@PreAuthorize("authentication.name == @defaultReportFacade.findTemplate(#templateId).get().user.username")
	void connectShow(Long templateId, Long showId, int order);
}
