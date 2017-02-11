package com.sandjelkovic.dispatchd.daemon;

import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.entities.UpdateJob;
import com.sandjelkovic.dispatchd.data.repositories.UpdateJobRepository;
import com.sandjelkovic.dispatchd.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.service.EpisodeService;
import com.sandjelkovic.dispatchd.service.TvShowService;
import com.sandjelkovic.dispatchd.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.trakt.importer.service.TraktImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.time.Instant;
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
@Component
public class ContentRefresher {
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

	public long refreshExistingContent() {
		ZonedDateTime fromTime = getLastUpdateTime();
		List<ShowUpdateTrakt> updatedShows = provider.getUpdates(fromTime.toLocalDate());

		List<String> traktIds = getTraktIds(updatedShows);

		// possible optimisation for failure cases -> scan internal db and compare retrieved.updatedAt < internal.lastLocalUpdate
		// in order to only update shows that failed in  the past. Since the update time is started from the last successful refresh.

		// independent update of each show in order to continue the refresh evens if some fail. Otherwise, this could have been one stream. (hint!, hint!)
		List<AsyncResult<TvShow>> importResults = traktIds.stream()
				.map(id -> traktImporterService.importShowAsync(id))
				.collect(toList());

		long count = importResults.stream()
				.map(getAsyncResultMapper())
				.count();

		saveJob();
		return count;
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
				.orElseGet(() -> ZonedDateTime.ofInstant(Instant.MIN, ZoneId.systemDefault())); // no successful updates before, do it for all.
	}
}
