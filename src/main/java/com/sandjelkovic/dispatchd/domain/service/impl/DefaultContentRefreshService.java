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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * @author ${sandjelkovic}
 * @date 28.1.17.
 */
@Service
public class DefaultContentRefreshService implements ContentRefreshService {
	@Autowired
	private EpisodeService episodeService;

	@Autowired
	private TvShowService tvShowService;

	@Autowired
	private UpdateJobRepository jobRepository;

	@Autowired
	private TraktMediaProvider provider;

	@Autowired
	private TraktImporterService traktImporterService;

	@Override
	public long refreshExistingContent() {
		ZonedDateTime fromTime = getLastUpdateTime();
		List<ShowUpdateTrakt> updatedShows = provider.getUpdates(fromTime.toLocalDate());

		List<String> traktIds = getTraktIds(updatedShows);

		List<String> idsForImport = filterForLocalIds(traktIds);

		// possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
		// in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.

		// independent update of each show in order to continue the refresh evens if some fail. Otherwise, this could have been one stream. (hint!, hint!)
		List<AsyncResult<TvShow>> importResults = idsForImport.stream()
				.map(id -> traktImporterService.importShowAsync(id))
				.collect(toList());

		long count = importResults.stream()
				.map(getAsyncResultMapper())
				.count();

		saveJob();
		return count;
	}

	private List<String> filterForLocalIds(List<String> traktIds) {
		return traktIds.stream()
				.filter(id -> !tvShowService.findByTraktId(id).isEmpty())
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
		jobRepository.save(new UpdateJob()
				.finishTime(ZonedDateTime.now())
				.success(true));
	}

	private ZonedDateTime getLastUpdateTime() {
		return jobRepository.findFirstBySuccessOrderByFinishTimeDesc(true)
				.map(UpdateJob::getFinishTime)
				.orElseGet(() -> ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault())); // no successful updates before, do it for all.
	}
}
