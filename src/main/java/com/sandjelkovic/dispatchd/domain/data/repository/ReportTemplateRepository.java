package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.List;

public interface ReportTemplateRepository extends PagingAndSortingRepository<ReportTemplate, Long> {
	List<ReportTemplate> findByActiveTrueAndTimeToGenerateReportIsBetween(ZonedDateTime min, ZonedDateTime max);

	List<ReportTemplate> findByActiveTrueAndTimeToGenerateReportIsBefore(ZonedDateTime timeToGenerateReport);

	@Override
	ReportTemplate save(@Valid ReportTemplate entity);

	@Override
	List<ReportTemplate> findAll();

	@Override
	Page<ReportTemplate> findAll(Pageable pageable);

	Page<ReportTemplate> findByUserId(Pageable pageable, Long userId);

	@Override
	ReportTemplate findOne(Long id);

	@Override
	boolean exists(Long id);

	@Override
	@PreAuthorize("authentication.name == @defaultReportService.findGenerated(#id).get().reportTemplate.user.username")
	void delete(Long id);

	@Override
	@PreAuthorize("authentication.name == @defaultReportService.findGenerated(#entity.id).get().reportTemplate.user.username")
	void delete(ReportTemplate entity);
}
