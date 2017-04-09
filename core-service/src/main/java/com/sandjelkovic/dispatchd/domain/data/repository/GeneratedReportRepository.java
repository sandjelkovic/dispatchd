package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.validation.Valid;

public interface GeneratedReportRepository extends PagingAndSortingRepository<GeneratedReport, Long> {
	@Override
	GeneratedReport save(@Valid GeneratedReport entity);

	Page<GeneratedReport> findByReportTemplateUserUsername(Pageable pageable, String username);

	Page<GeneratedReport> findByReportTemplateIdAndReportTemplateUserUsername(Pageable pageable, Long templateId, String username);

	@Override
	GeneratedReport findOne(Long id);

	@Override
	boolean exists(Long id);

	@Override
	@PreAuthorize("authentication.name == @generatedReportRepository.findOne(#id).get().user.username")
	void delete(Long id);

	@Override
	@PreAuthorize("authentication.name == @generatedReportRepository.findOne(#entity.id).get().user.username")
	void delete(GeneratedReport entity);
}
