package com.sandjelkovic.dispatchd.provider;

import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;

import java.util.List;

public interface TraktMediaProvider {

	public TvShowTrakt getTvShow(String showId);

	public EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber);

	public List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber);

	public List<EpisodeTrakt> getShowEpisodes(String showId);

	public List<SeasonTrakt> getSeasons(String showId);

	public List<SeasonTrakt> getSeasonsMinimal(String showId);

	public SeasonTrakt getSeason(String showId, String seasonNumber);

}
