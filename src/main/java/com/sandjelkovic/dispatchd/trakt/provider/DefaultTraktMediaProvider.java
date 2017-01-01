package com.sandjelkovic.dispatchd.trakt.provider;

import com.sandjelkovic.dispatchd.configuration.TraktConfiguration;
import com.sandjelkovic.dispatchd.configuration.TraktConstants;
import com.sandjelkovic.dispatchd.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Component
public class DefaultTraktMediaProvider implements TraktMediaProvider {

	@Resource(name = "traktRestTemplate")
	private RestTemplate restTemplate;

	@Autowired
	private TraktConfiguration traktConfiguration;

	@Override
	public TvShowTrakt getTvShow(String showId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows")
				.pathSegment(showId)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		TvShowTrakt response = restTemplate.getForObject(finalUri, TvShowTrakt.class);
		return response;
	}

	public EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons", seasonNumber)
				.pathSegment("episodes", episodeNumber)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		EpisodeTrakt response = restTemplate.getForObject(finalUri, EpisodeTrakt.class);
		return response;
	}

	public List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons", seasonNumber)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		EpisodeTrakt[] response = restTemplate.getForObject(finalUri, EpisodeTrakt[].class);
		return Arrays.asList(response);
	}

	public List<EpisodeTrakt> getShowEpisodes(String showId) {
		List<SeasonTrakt> seasons = getSeasonsMinimal(showId);
		return seasons.stream()
				.map(season -> getSeasonEpisodes(showId, season.getNumber()))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	public List<SeasonTrakt> getSeasons(String showId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons")
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		return Arrays.asList(response);
	}

	public List<SeasonTrakt> getSeasonsMinimal(String showId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons")
				.queryParam("extended", TraktConstants.EXTENSION_MINIMAL);
		final URI finalUri = builder.build().encode().toUri();
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		return Arrays.asList(response);
	}

	public SeasonTrakt getSeason(String showId, String seasonNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons")
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		SeasonTrakt season = (SeasonTrakt) extractItemFromArray(response, Integer.valueOf(seasonNumber));
		return season;
	}

	@Override
	public Future<TvShowTrakt> getTvShowAsync(String showId) {
		return new AsyncResult<>(getTvShow(showId));
	}

	@Override
	public Future<EpisodeTrakt> getEpisodeAsync(String showId, String seasonNumber, String episodeNumber) {
		return new AsyncResult<>(getEpisode(showId, seasonNumber, episodeNumber));
	}

	@Override
	public Future<List<EpisodeTrakt>> getSeasonEpisodesAsync(String showId, String seasonNumber) {
		return new AsyncResult<>(getSeasonEpisodes(showId, seasonNumber));
	}

	@Override
	public Future<List<EpisodeTrakt>> getShowEpisodesAsync(String showId) {
		return new AsyncResult<>(getShowEpisodes(showId));
	}

	@Override
	public Future<List<SeasonTrakt>> getSeasonsAsync(String showId) {
		return new AsyncResult<>(getSeasons(showId));
	}

	@Override
	public Future<List<SeasonTrakt>> getSeasonsMinimalAsync(String showId) {
		return new AsyncResult<>(getSeasonsMinimal(showId));
	}

	@Override
	public Future<SeasonTrakt> getSeasonAsync(String showId, String seasonNumber) {
		return new AsyncResult<>(getSeason(showId, seasonNumber));
	}

	private Object extractItemFromArray(Object[] array, Integer index) {
		return (array.length > index) ? array[index] : null;
	}

}
