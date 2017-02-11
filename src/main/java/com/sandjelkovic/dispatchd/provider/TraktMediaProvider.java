package com.sandjelkovic.dispatchd.provider;

import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;
import org.springframework.scheduling.annotation.Async;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Future;

public interface TraktMediaProvider {

	TvShowTrakt getTvShow(String showId);

	EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber);

	List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber);

	List<EpisodeTrakt> getShowEpisodes(String showId);

	List<SeasonTrakt> getSeasons(String showId);

	List<SeasonTrakt> getSeasonsMinimal(String showId);

	SeasonTrakt getSeason(String showId, String seasonNumber);

	List<ShowUpdateTrakt> getUpdates(LocalDate fromDate);

	@Async
	Future<TvShowTrakt> getTvShowAsync(String showId);

	@Async
	Future<EpisodeTrakt> getEpisodeAsync(String showId, String seasonNumber, String episodeNumber);

	@Async
	Future<List<EpisodeTrakt>> getSeasonEpisodesAsync(String showId, String seasonNumber);

	@Async
	Future<List<EpisodeTrakt>> getShowEpisodesAsync(String showId);

	@Async
	Future<List<SeasonTrakt>> getSeasonsAsync(String showId);

	@Async
	Future<List<SeasonTrakt>> getSeasonsMinimalAsync(String showId);

	@Async
	Future<SeasonTrakt> getSeasonAsync(String showId, String seasonNumber);

	@Async
	Future<List<ShowUpdateTrakt>> getUpdatesAsync(LocalDate fromDate);

}
