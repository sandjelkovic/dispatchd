package com.sandjelkovic.dispatchd.content.trakt.provider.impl;

import com.sandjelkovic.dispatchd.content.configuration.TraktConfiguration;
import com.sandjelkovic.dispatchd.content.configuration.TraktConstants;
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class DefaultTraktMediaProvider implements TraktMediaProvider {

	private RestTemplate restTemplate;

	private TraktConfiguration traktConfiguration;

	@Override
	public TvShowTrakt getTvShow(String showId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows")
				.pathSegment(showId)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		TvShowTrakt response = restTemplate.getForObject(finalUri, TvShowTrakt.class);
		log.debug("Retrieved TvShow: " + response);
		return response;
	}

	public EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons", seasonNumber)
				.pathSegment("episodes", episodeNumber)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		EpisodeTrakt response = restTemplate.getForObject(finalUri, EpisodeTrakt.class);
		log.debug("Retrieved Episode: " + response);
		return response;
	}

	public List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons", seasonNumber)
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		EpisodeTrakt[] response = restTemplate.getForObject(finalUri, EpisodeTrakt[].class);
		List<EpisodeTrakt> episodesResponse = Arrays.asList(response);
		log.debug("Retrieved SeasonEpisodes: " + episodesResponse);
		return episodesResponse;
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
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		List<SeasonTrakt> seasonsResponse = Arrays.asList(response);
		log.debug("Retrieved Seasons: " + seasonsResponse);
		return seasonsResponse;
	}

	public List<SeasonTrakt> getSeasonsMinimal(String showId) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons")
				.queryParam("extended", TraktConstants.EXTENSION_MINIMAL);
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		log.debug("Retrieved minimal Seasons: " + response);
		return Arrays.asList(response);
	}

	public SeasonTrakt getSeason(String showId, String seasonNumber) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows", showId)
				.pathSegment("seasons")
				.queryParam("extended", TraktConstants.EXTENSION_FULL_IMAGES);
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		SeasonTrakt[] response = restTemplate.getForObject(finalUri, SeasonTrakt[].class);
		SeasonTrakt season = (SeasonTrakt) extractItemFromArray(response, Integer.valueOf(seasonNumber));
		log.debug("Retrieved Season: " + season);
		return season;
	}

	public List<ShowUpdateTrakt> getUpdates(LocalDate fromDate) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(traktConfiguration.getBaseServiceUrl())
				.pathSegment("shows")
				.pathSegment("updates")
				.pathSegment(fromDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
		final URI finalUri = builder.build().encode().toUri();
		log.debug("Calling Trakt API for URL: [ " + finalUri + " ]");
		ShowUpdateTrakt[] response = restTemplate.getForObject(finalUri, ShowUpdateTrakt[].class);
		List<ShowUpdateTrakt> responseList = Arrays.asList(response);
		log.debug("Retrieved Updates: " + Arrays.toString(response));
		return responseList;
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

	@Override
	public Future<List<ShowUpdateTrakt>> getUpdatesAsync(LocalDate fromDate) {
		return new AsyncResult<>(getUpdates(fromDate));
	}

	private Object extractItemFromArray(Object[] array, Integer index) {
		return (array.length > index) ? array[index] : null;
	}

}
