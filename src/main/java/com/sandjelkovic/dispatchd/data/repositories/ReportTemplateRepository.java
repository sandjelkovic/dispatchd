package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

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
}