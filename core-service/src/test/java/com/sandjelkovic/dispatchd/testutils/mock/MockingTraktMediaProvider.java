package com.sandjelkovic.dispatchd.testutils.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandjelkovic.dispatchd.common.helper.EmptyCollections;
import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.ShowUpdateTrakt;
import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.testutils.exceptions.DataNotSetupException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.AsyncResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class MockingTraktMediaProvider implements TraktMediaProvider {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public TvShowTrakt getTvShow(String showId) {
		try {
			return objectMapper.readValue(getJsonFile(showId, "", "show"), TvShowTrakt.class);
		} catch (IOException e) {
			log.error("Failed while retrieving stubs from classpath.", e);
			throw new DataNotSetupException();
		}
	}

	public EpisodeTrakt getEpisode(String showId, String seasonNumber, String episodeNumber) {
		Integer epNumber = Integer.valueOf(episodeNumber);
		return getSeasonEpisodes(showId, seasonNumber).stream()
				.filter(episodeTrakt -> episodeTrakt.getNumber().equals(epNumber))
				.findAny()
				.orElseThrow(DataNotSetupException::new);
	}

	public List<EpisodeTrakt> getSeasonEpisodes(String showId, String seasonNumber) {
		try {
			return Arrays.asList(objectMapper.readValue(getJsonFile(showId, "seasons/" + seasonNumber + "/", "episodes"), EpisodeTrakt[].class));
		} catch (IOException e) {
			log.error("Failed while retrieving stubs from classpath.", e);
			throw new DataNotSetupException();
		}
	}

	public List<EpisodeTrakt> getShowEpisodes(String showId) {
		List<SeasonTrakt> seasons = getSeasonsMinimal(showId);
		return seasons.stream()
				.map(season -> getSeasonEpisodes(showId, season.getNumber()))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	public List<SeasonTrakt> getSeasons(String showId) {
		try {
			return Arrays.asList(objectMapper.readValue(getJsonFile(showId, "", "seasons"), SeasonTrakt[].class));
		} catch (IOException e) {
			log.error("Failed while retrieving stubs from classpath.", e);
			throw new DataNotSetupException();
		}
	}

	public List<SeasonTrakt> getSeasonsMinimal(String showId) {
		return getSeasons(showId);
	}

	public SeasonTrakt getSeason(String showId, String seasonNumber) {
		return getSeasons(showId).stream()
				.filter(seasonTrakt -> seasonTrakt.getNumber().equals(seasonNumber))
				.findAny()
				.orElseThrow(DataNotSetupException::new);
	}

	public List<ShowUpdateTrakt> getUpdates(LocalDate fromDate) {
		return EmptyCollections.list();
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

	private File getJsonFile(String showId, String pathAddition, String fileName) throws IOException {
		String s = "stubs/shows/" + showId + "/" + pathAddition + "/" + fileName + ".json";
		return new ClassPathResource(s).getFile();
	}

}
