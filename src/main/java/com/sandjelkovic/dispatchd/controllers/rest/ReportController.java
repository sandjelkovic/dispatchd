package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.api.resources.ReportDTOResource;
import com.sandjelkovic.dispatchd.api.resources.UserReportContentResource;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReportContent;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import com.sandjelkovic.dispatchd.service.ReportService;
import com.sandjelkovic.dispatchd.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = Constants.REST_ENDPOINT_API_PREFIX + "/reports",
		produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE},
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class ReportController {
	private static final Logger log = LoggerFactory.getLogger(ReportController.class);

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = GET)
	public List<ReportDTO> getReportsForCurrentUser() {
		return new ArrayList<>();
	}

	@RequestMapping(value = "/{reportId}", method = GET)
	public ReportDTOResource getReport(@PathVariable Long reportId) {
		GeneratedReport generatedReport = reportService.findPublishedGenerated(reportId)
				.orElseThrow(ResourceNotFoundException::new);
		return conversionService.convert(generatedReport, ReportDTOResource.class);
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