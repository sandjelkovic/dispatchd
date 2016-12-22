package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class EpisodeEntityConverter {

	private Logger log = LoggerFactory.getLogger(EpisodeEntityConverter.class);

	public Episode convertFrom(EpisodeTrakt trakt) {
		Episode episode = new Episode();
		episode.setTitle(trakt.getTitle());
		episode.setDescription(trakt.getOverview());
		episode.setNumber(trakt.getNumber());
		Optional.ofNullable(trakt.getFirstAired())
				.map(time -> ZonedDateTime.ofInstant(time, ZoneOffset.UTC))
				.ifPresent(episode::setAirdate);
		episode.setSeasonNumber(trakt.getSeason());
		episode.setTraktId(trakt.getIds().get("trakt"));
		episode.setImdbId(trakt.getIds().getOrDefault("imdb", ""));
		Optional.ofNullable(trakt.getUpdatedAt())
				.map(time -> ZonedDateTime.ofInstant(time, ZoneOffset.UTC))
				.ifPresent(episode::setLastUpdated);

		return episode;
	}
}
