package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ${sandjelkovic}
 * @date 11.2.17.
 */
public interface ReportGeneratorService {
	@Transactional
	void generateReports();

	@Transactional
	GeneratedReport generateReportFromTemplate(ReportTemplate reportTemplate);
}
