package com.sandjelkovic.dispatchd.content.trakt.converter;

import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class Trakt2TvShowConverter implements Converter<TvShowTrakt, TvShow> {

	@Override
	public TvShow convert(TvShowTrakt source) {
		TvShow newShow = new TvShow();
		newShow.setTitle(source.getTitle());
		newShow.setDescription(source.getOverview());
		newShow.setStatus(source.getStatus());
		newShow.setYear(source.getYear());
		newShow.setTraktId(source.getIds().get("trakt"));
		newShow.setImdbId(source.getIds().getOrDefault("imdb", ""));
		newShow.setSeasons(new ArrayList<>());
		newShow.setEpisodes(new ArrayList<>());
		newShow.setLastLocalUpdate(Timestamp.from(Instant.now()));
		return newShow;
	}
}
