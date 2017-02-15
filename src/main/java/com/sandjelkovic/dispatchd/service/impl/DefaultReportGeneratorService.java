package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.configuration.ValuesConfiguration;
import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReportContent;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate2TvShow;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.event.GeneratedReportEvent;
import com.sandjelkovic.dispatchd.service.ReportGeneratorService;
import com.sandjelkovic.dispatchd.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class DefaultReportGeneratorService implements ReportGeneratorService {

	private static final Logger log = LoggerFactory.getLogger(DefaultReportGeneratorService.class);

	@Autowired
	private ValuesConfiguration valuesConfiguration;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ReportService reportService;

	@Autowired
	@Qualifier(Constants.CONVERSION_SERVICE_BEAN_NAME)
	private ConversionService conversionService;

	@Override
	@Transactional
	public void generateReports() {
		ZonedDateTime time = ZonedDateTime.now().plusSeconds(valuesConfiguration.getGenerationInterval());
		log.debug("Generating reports until time [" + time + "]");
		List<ReportTemplate> templates = reportService.getReportTemplatesToBeGeneratedBetween(null, time);
		templates.stream()
				.map(this::generateReportFromTemplate)
				.collect(toList()) // make sure that all reports are generated since this whole method is transactional
				.forEach(this::publishEvent);
	}

	@Override
	public GeneratedReport generateReportFromTemplate(ReportTemplate reportTemplate) {
		List<ReportTemplate2TvShow> sortedRelations = getSortedRelationsWithTvShow(reportTemplate);
		List<Episode> episodes = getEpisodesValidForReport(reportTemplate, sortedRelations);

		GeneratedReport generatedReport = new GeneratedReport();
		generatedReport.setReportTemplate(reportTemplate);
		generatedReport.setText(generateReportText(reportTemplate, generatedReport));
		generatedReport = reportService.save(generatedReport);

		addEpisodesToReportAsContents(generatedReport, episodes);
		updateGenerationTimes(reportTemplate);
		return generatedReport;
	}

	private List<ReportTemplate2TvShow> getSortedRelationsWithTvShow(ReportTemplate reportTemplate) {
		return reportTemplate.getReportTemplate2TvShows().stream()
				.sorted(Comparator.comparingInt(ReportTemplate2TvShow::getOrderInReport))
				.collect(toList());
	}

	private void updateGenerationTimes(ReportTemplate reportTemplate) {
		reportTemplate.setTimeOfLastGeneratedReport(reportTemplate.getTimeToGenerateReport());
		reportTemplate.setTimeToGenerateReport(getNewGenerationTime(reportTemplate));

		reportService.saveNoUserContext(reportTemplate);
	}

	private List<Episode> getEpisodesValidForReport(ReportTemplate reportTemplate, List<ReportTemplate2TvShow> sortedRelations) {
		return sortedRelations.stream()
				.map(ReportTemplate2TvShow::getTvShow)
				.map(TvShow::getEpisodes)
				.flatMap(Collection::stream)
				.filter(episode -> episode.getAirdate().isBefore(reportTemplate.getTimeToGenerateReport()))
				.filter(episode -> episode.getAirdate().isAfter(reportTemplate.getTimeOfLastGeneratedReport()))
				.collect(toList());
	}

	@Async
	private void publishEvent(GeneratedReport generatedReport) {
		eventPublisher.publishEvent(new GeneratedReportEvent(generatedReport));
	}

	private ZonedDateTime getNewGenerationTime(ReportTemplate reportTemplate) {
		return reportService.getNewGenerationTimeForTemplate(reportTemplate);
	}

	private String generateReportText(ReportTemplate reportTemplate, GeneratedReport generatedReport) {
		return reportTemplate.getDescription();
	}

//				generatedReport.getGeneratedReportContents().stream()
//				.sorted(this::compareEntryOrder)
//				.map(GeneratedReportContent::getEpisode)
//				.map(episode -> transformEpisodeToReportContentText(episode))
//				.collect(Collectors.joining(" <br> "));

//	private String transformEpisodeToReportContentText(Episode episode) {
//		StringBuilder sb = new StringBuilder();
//		sb.append(episode.getTvShow().getTitle())
//				.append(" s")
//				.append(episode.getSeasonNumber())
//				.append("e")
//				.append(episode.getNumber())
//				.append(" : ")
//				.append(episode.getTitle())
//				.append(" : aired on ")
//				.append(episode.getAirdate());
//	}

	private void addEpisodesToReportAsContents(GeneratedReport generatedReport, List<Episode> episodes) {
		int orderNumber = 0;
		for (Episode episode : episodes) {
			GeneratedReportContent content = new GeneratedReportContent();
			content.setOrderInReport(orderNumber++);
			content.setEpisode(episode);
			content.setGeneratedReport(generatedReport);
			generatedReport.addGeneratedReportContent(content);
		}
	}

}
