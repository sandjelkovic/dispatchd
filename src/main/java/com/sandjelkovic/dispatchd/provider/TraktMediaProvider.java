package com.sandjelkovic.dispatchd.provider;

import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;

import java.util.List;

public interface TraktMediaProvider {

	TvShowTrakt getTvShow(String showId);

	EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber);

	List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber);

	List<EpisodeTrakt> getShowEpisodes(String showId);

	List<SeasonTrakt> getSeasons(String showId);

	List<SeasonTrakt> getSeasonsMinimal(String showId);

	SeasonTrakt getSeason(String showId, String seasonNumber);

}
