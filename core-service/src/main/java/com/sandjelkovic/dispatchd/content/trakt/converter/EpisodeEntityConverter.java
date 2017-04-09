package com.sandjelkovic.dispatchd.content.trakt.converter;

import com.sandjelkovic.dispatchd.content.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class EpisodeEntityConverter implements Converter<EpisodeTrakt, Episode> {

	@Override
	public Episode convert(EpisodeTrakt source) {
		Episode episode = new Episode();
		episode.setTitle(source.getTitle());
		episode.setDescription(source.getOverview());
		episode.setNumber(source.getNumber());
		Optional.ofNullable(source.getFirstAired())
				.map(time -> ZonedDateTime.ofInstant(time, ZoneOffset.UTC))
				.ifPresent(episode::setAirdate);
		episode.setSeasonNumber(source.getSeason());
		episode.setTraktId(source.getIds().get("trakt"));
		episode.setImdbId(source.getIds().getOrDefault("imdb", ""));
		Optional.ofNullable(source.getUpdatedAt())
				.map(time -> ZonedDateTime.ofInstant(time, ZoneOffset.UTC))
				.ifPresent(episode::setLastUpdated);

		return episode;
	}
}
