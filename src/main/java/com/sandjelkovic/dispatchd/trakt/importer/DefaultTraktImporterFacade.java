package com.sandjelkovic.dispatchd.trakt.importer;

import com.sandjelkovic.dispatchd.data.dto.ImportStatusDto;
import com.sandjelkovic.dispatchd.data.entities.ImportProgressStatus;
import com.sandjelkovic.dispatchd.data.entities.ImportStatus;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.event.ImportMovieEvent;
import com.sandjelkovic.dispatchd.event.ImportTvShowEvent;
import com.sandjelkovic.dispatchd.event.TvShowImportedEvent;
import com.sandjelkovic.dispatchd.event.listener.ImportEventListener;
import com.sandjelkovic.dispatchd.exception.InvalidMediaImportUrlException;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import com.sandjelkovic.dispatchd.facade.ImporterFacade;
import com.sandjelkovic.dispatchd.trakt.importer.exception.ShowAlreadyExistsImporterException;
import com.sandjelkovic.dispatchd.trakt.importer.exception.TraktServerException;
import com.sandjelkovic.dispatchd.trakt.importer.service.TraktImporterService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultTraktImporterFacade implements ImporterFacade, ImportEventListener {

	private static final Logger log = LoggerFactory.getLogger(DefaultTraktImporterFacade.class);

	@Autowired
	private TraktImporterService traktImporterService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private ConversionService conversionService;

	@Override
	public ImportStatusDto getImportStatus(Long id) {
		ImportStatus status = Optional.ofNullable(traktImporterService.findOneImportStatus(id))
				.orElseThrow(ResourceNotFoundException::new);
		return conversionService.convert(status, ImportStatusDto.class);
	}

	@Override
	public ImportStatusDto importFromUriComponents(UriComponents uriComponents) {
		log.debug("importFromUri");
		List<String> segments = uriComponents.getPathSegments();
		// /shows/{id}
		if (segments.size() < 2) {
			throw new InvalidMediaImportUrlException();
		}
		ImportStatus status = traktImporterService.createNewImportStatus(segments);
		pickAndDoImport(segments, segments.get(0), status);
		return conversionService.convert(traktImporterService.findOneImportStatus(status.getId()), ImportStatusDto.class);
	}

	private void pickAndDoImport(List<String> segments, String type, ImportStatus status) {
		if (StringUtils.equals(type, "shows")) {
			publishImportTvShow(segments, status);
		} else if (StringUtils.equals(type, "movies")) {
			publishImportMovie(segments, status);
		}
	}

	public void publishImportTvShow(List<String> segments, ImportStatus status) {
		eventPublisher.publishEvent(new ImportTvShowEvent(segments, status));
	}

	public void publishImportMovie(List<String> segments, ImportStatus status) {
		eventPublisher.publishEvent(new ImportMovieEvent(segments, status));
	}

	@EventListener
	public void handleImportMovie(ImportMovieEvent event) {
	}

	@EventListener
	@Async
	public void handleImportShow(ImportTvShowEvent event) {
		log.debug("handleImportShow");
		ImportStatus importStatus = event.getStatus();
		try {
			importStatus = updateStatus(importStatus, ImportProgressStatus.IN_PROGRESS);
			TvShow importedShow = traktImporterService.importShow(event.getSegments());
			updateStatus(importStatus, ImportProgressStatus.SUCCESS);
			eventPublisher.publishEvent(new TvShowImportedEvent(importedShow));
		} catch (ShowAlreadyExistsImporterException exception) {
			updateStatus(importStatus, ImportProgressStatus.SHOW_ALREADY_EXISTS);
		} catch (TraktServerException exception) {
			updateStatus(importStatus, ImportProgressStatus.REMOTE_SERVER_ERROR);
		} catch (Exception exception) {
			updateStatus(importStatus, ImportProgressStatus.ERROR);
			log.error("During show importing an unforeseen error has occurred", exception);
		}
	}

	public ImportStatus updateStatus(ImportStatus status, ImportProgressStatus progress) {
		ImportStatus importStatus = traktImporterService.findOneImportStatus(status.getId());
		importStatus.setFinishTime(ZonedDateTime.now());
		importStatus.setStatus(progress);
		return traktImporterService.save(importStatus);
	}
}
