package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.api.resource.ReportResource;
import com.sandjelkovic.dispatchd.api.resource.UserReportContentResource;
import com.sandjelkovic.dispatchd.api.resource.UserReportListResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReportContent;
import com.sandjelkovic.dispatchd.domain.service.ReportService;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/reports",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
@Slf4j
public class ReportController extends BaseController {

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportService reportService;

	@RequestMapping(method = GET)
	public UserReportListResource getReportsForCurrentUser(@RequestParam(required = false) Long templateId,
	                                                       Principal principal, Pageable pageable) {
		Page<GeneratedReport> reportPage = reportService.findGeneratedByTemplateForUser(pageable, templateId, principal.getName());
		return new UserReportListResource(reportPage.map(source -> conversionService.convert(source, ReportDTO.class)));
	}

	@RequestMapping(value = "/{reportId}", method = GET)
	public ReportResource getReport(@PathVariable Long reportId) {
		GeneratedReport generatedReport = reportService.findGenerated(reportId)
				.orElseThrow(ResourceNotFoundException::new);
		ReportResource reportResource = conversionService.convert(generatedReport, ReportResource.class);
		return resourceProcessorInvoker.invokeProcessorsFor(reportResource);
	}

	@RequestMapping(value = "/{reportId}/content", method = GET)
	public UserReportContentResource getReportContents(@PathVariable Long reportId, Pageable pageable) {
		GeneratedReport report = reportService.findGenerated(reportId)
				.orElseThrow(ResourceNotFoundException::new);
		List<EpisodeDTO> episodeDTOs = report.getGeneratedReportContents().stream()
				.sorted(Comparator.comparingInt(GeneratedReportContent::getOrderInReport))
				.map(GeneratedReportContent::getEpisode)
				.map(episode -> conversionService.convert(episode, EpisodeDTO.class))
				.collect(Collectors.toList());

		//// TODO: 18.12.16. Implement proper paging logic, not just return everything
		PageImpl<EpisodeDTO> page = new PageImpl<>(episodeDTOs);
		ReportDTO reportDto = conversionService.convert(report, ReportDTO.class);
		return new UserReportContentResource(page, reportDto);
	}

}
