package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
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
