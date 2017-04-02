package com.sandjelkovic.dispatchd.content.trakt.importer.service.impl;

import com.sandjelkovic.dispatchd.common.helper.EmptyCollections;
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.content.trakt.importer.exception.TraktServerException;
import com.sandjelkovic.dispatchd.content.trakt.importer.service.TraktImporterService;
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.ImportProgressStatus;
import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;
import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.repository.ImportStatusRepository;
import com.sandjelkovic.dispatchd.domain.service.EpisodeService;
import com.sandjelkovic.dispatchd.domain.service.SeasonService;
import com.sandjelkovic.dispatchd.domain.service.TvShowService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@Slf4j
public class DefaultTraktImporterService implements TraktImporterService {

	@Autowired
	private TraktMediaProvider provider;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private TvShowService tvShowService;

	@Autowired
	private SeasonService seasonService;

	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private ImportStatusRepository importStatusRepository;

	@Override
	@Transactional
	public TvShow importShow(List<String> segments) {
		log.debug("DefaultTraktImporterService.importShow");
		String showId = segments.get(1);
		return importShow(showId);
	}

	@Override
	public TvShow importShow(String showId) {
		TvShowTrakt traktShow = getTvShowFromTrakt(showId);
		Future<List<SeasonTrakt>> seasonsFuture = getSeasonsFromTraktAsync(showId);
		Future<List<EpisodeTrakt>> episodesFuture = getEpisodesFromTraktAsync(showId);

		TvShow show = tvShowService.save(conversionService.convert(traktShow, TvShow.class));

		List<Season> seasonsList = retrieveAndConvertSeasons(seasonsFuture);
		seasonsList.forEach(season -> season.setTvShow(show));
		seasonsList = seasonService.save(seasonsList);
		Map<String, Season> seasonsMap = seasonsList.stream()
				.collect(toMap(Season::getNumber, s -> s));

		List<Episode> episodeList = retrieveAndConvertEpisodes(episodesFuture);
		episodeList.forEach(episode -> {
			episode.setTvShow(show);
			episode.setSeason(seasonsMap.getOrDefault(episode.getSeasonNumber(), new Season()));
		});
		episodeService.save(episodeList);
		return tvShowService.findOne(show.getId()).get();
	}

	@Override
	public AsyncResult<TvShow> importShowAsync(String showId) {
		return new AsyncResult<>(importShow(showId));
	}
	@Override
	public ImportStatus createNewImportStatus(List<String> segments) {
		ImportStatus status = new ImportStatus();
		status.setMediaUrl(StringUtils.join(segments, "/"));
		status.setStatus(ImportProgressStatus.QUEUED);
		status.setInitiationTime(ZonedDateTime.now());

		return importStatusRepository.save(status);
	}

	@Override
	public ImportStatus save(ImportStatus status) {
		return importStatusRepository.save(status);
	}

	@Override
	public ImportStatus findOneImportStatus(Long id) {
		return importStatusRepository.findOne(id);
	}

	private TvShowTrakt getTvShowFromTrakt(String showId) {
		try {
			return provider.getTvShow(showId);
		} catch (HttpClientErrorException e) {
			throw new TraktServerException(e);
		}
	}

	private List<Season> retrieveAndConvertSeasons(Future<List<SeasonTrakt>> seasonsFuture) {
		return retrieveSeasonsFromFuture(seasonsFuture).stream()
				.map(seasonTrakt -> conversionService.convert(seasonTrakt, Season.class))
				.collect(toList());
	}

	private List<SeasonTrakt> retrieveSeasonsFromFuture(Future<List<SeasonTrakt>> seasonsFuture) {
		try {
			return seasonsFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return EmptyCollections.list();
	}


	private List<Episode> retrieveAndConvertEpisodes(Future<List<EpisodeTrakt>> episodesFuture) {
		return retrieveEpisodesFromFuture(episodesFuture).stream()
				.map(episodeTrakt -> conversionService.convert(episodeTrakt, Episode.class))
				.collect(toList());
	}

	private List<EpisodeTrakt> retrieveEpisodesFromFuture(Future<List<EpisodeTrakt>> episodesFuture) {
		try {
			return episodesFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			log.error(e.getMessage(), e.getCause());
		}
		return EmptyCollections.list();
	}

	private Future<List<SeasonTrakt>> getSeasonsFromTraktAsync(String showId) {
		return provider.getSeasonsAsync(showId);
	}

	private Future<List<EpisodeTrakt>> getEpisodesFromTraktAsync(String showId) {
		return provider.getShowEpisodesAsync(showId);
	}

}
