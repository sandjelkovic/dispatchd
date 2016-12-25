package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.entities.*;
import com.sandjelkovic.dispatchd.event.GeneratedReportEvent;
import com.sandjelkovic.dispatchd.helper.EventDispatcher;
import com.sandjelkovic.dispatchd.service.ReportGenerationJobService;
import com.sandjelkovic.dispatchd.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class ReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(ReportGenerator.class);

	@Value(value = "${report.generation.interval.seconds:60}")
	private int generationInterval;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private EventDispatcher eventDispatcher;

	@Autowired
	private ReportGenerationJobService jobService;

	@Autowired
	private ReportService reportService;

	@Autowired
	private MailSender mailSender;

	@Autowired
	@Qualifier(Constants.CONVERSION_SERVICE_BEAN_NAME)
	private ConversionService conversionService;

	@Transactional
	public void generateReports() {
		ZonedDateTime time = ZonedDateTime.now().plusSeconds(generationInterval);
		log.debug("Generating reports until time [" + time + "]");
		List<ReportTemplate> templates = reportService.getReportTemplatesToBeGeneratedBetween(null, time);
		templates.stream()
				.map(this::generateReportFromTemplate)
				.collect(toList()) // make sure that all reports are generated since this whole method is transactional
				.forEach(this::publishEvent);
	}

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
				.sorted(this::compareShowOrder)
				.collect(toList());
	}

	private void updateGenerationTimes(ReportTemplate reportTemplate) {
		reportTemplate.setTimeOfLastGeneratedReport(reportTemplate.getTimeToGenerateReport());
		reportTemplate.setTimeToGenerateReport(getNewGenerationTime(reportTemplate));

		reportService.save(reportTemplate);
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

	private int compareEntryOrder(GeneratedReportContent first, GeneratedReportContent second) {
		if (first.getOrderInReport() > second.getOrderInReport()) {
			return 1;
		} else if (first.getOrderInReport() < second.getOrderInReport()) {
			return -1;
		}
		return 0;
	}

	private int compareShowOrder(ReportTemplate2TvShow first, ReportTemplate2TvShow second) {
		if (first.getOrderInReport() > second.getOrderInReport()) {
			return 1;
		} else if (first.getOrderInReport() < second.getOrderInReport()) {
			return -1;
		}
		return 0;
	}

	public int getGenerationInterval() {
		return generationInterval;
	}
}
