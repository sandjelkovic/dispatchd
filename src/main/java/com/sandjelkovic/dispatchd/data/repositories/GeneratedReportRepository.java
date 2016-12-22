package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.validation.Valid;

public interface GeneratedReportRepository extends PagingAndSortingRepository<GeneratedReport, Long> {
	@Override
	GeneratedReport save(@Valid GeneratedReport entity);
}
