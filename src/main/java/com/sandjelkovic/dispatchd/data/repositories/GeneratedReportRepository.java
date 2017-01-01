package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.Valid;

public interface GeneratedReportRepository extends PagingAndSortingRepository<GeneratedReport, Long> {
	@Override
	GeneratedReport save(@Valid GeneratedReport entity);

	Page<GeneratedReport> findByReportTemplateUserUsername(Pageable pageable, String username);

	Page<GeneratedReport> findByReportTemplateIdAndReportTemplateUserUsername(Pageable pageable, Long templateId, String username);
}
