package com.sandjelkovic.dispatchd.domain.service.impl;

import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.content.trakt.importer.service.TraktImporterService;
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.UpdateJob;
import com.sandjelkovic.dispatchd.domain.data.repository.UpdateJobRepository;
import com.sandjelkovic.dispatchd.domain.service.ContentRefreshService;
import com.sandjelkovic.dispatchd.domain.service.EpisodeService;
import com.sandjelkovic.dispatchd.domain.service.TvShowService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author ${sandjelkovic}
 * @date 28.1.17.
 */
@Service
@Slf4j
@AllArgsConstructor
public class DefaultContentRefreshService implements ContentRefreshService {
	private EpisodeService episodeService;

	private TvShowService tvShowService;

	private UpdateJobRepository jobRepository;

	private TraktMediaProvider provider;

	private TraktImporterService traktImporterService;

	@Override
	public long refreshExistingContent() {
		ZonedDateTime fromTime = getLastUpdateTime();
		log.debug("Refreshing content. Last update was: " + fromTime);
		List<ShowUpdateTrakt> updatedShows = provider.getUpdates(fromTime.toLocalDate());

		List<String> traktIds = getTraktIds(updatedShows);
		List<String> idsForImport = filterForLocalIds(traktIds);
		log.debug("Shows to be imported (Trakt IDs): " + traktIds.toString());

		// possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
		// in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.

		// independent update of each show in order to continue the refresh evens if some fail. Otherwise, this could have been one stream. (hint!, hint!)
		List<AsyncResult<TvShow>> importResults = idsForImport.stream()
				.map(id -> traktImporterService.importShowAsync(id))
				.collect(toList());

		long count = importResults.stream()
				.map(getAsyncResultMapper())
				.filter(Objects::nonNull)
				.count();

		saveJob();
		return count;
	}

	private List<String> filterForLocalIds(List<String> traktIds) {
		return traktIds.stream()
				.filter(id -> tvShowService.findByTraktId(id).isPresent())
				.collect(toList());
	}

	private Function<AsyncResult<TvShow>, TvShow> getAsyncResultMapper() {
		return tvShowAsyncResult -> {
			try {
				return tvShowAsyncResult.get(1, TimeUnit.MINUTES);
			} catch (ExecutionException ignored) {
				return null;
			}
		};
	}

	private List<String> getTraktIds(List<ShowUpdateTrakt> updatedShows) {
		return updatedShows.stream()
				.map(ShowUpdateTrakt::getShow)
				.map(TvShowTrakt::getIds)
				.filter(idMap -> idMap.containsKey("trakt"))
				.map(idMap -> idMap.get("trakt"))
				.collect(toList());
	}

	private void saveJob() {
		UpdateJob job = jobRepository.save(new UpdateJob()
				.finishTime(ZonedDateTime.now())
				.success(true));
		log.debug("Saved job: " + job);
	}

	private ZonedDateTime getLastUpdateTime() {
		return jobRepository.findFirstBySuccessOrderByFinishTimeDesc(true)
				.map(UpdateJob::getFinishTime)
				.orElseGet(() -> ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault())); // no successful updates before, do it for all.
	}
}
