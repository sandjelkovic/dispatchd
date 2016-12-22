package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.trakt.dto.TvShowTrakt;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

@Component
public class TvShowEntityConverter {
	public TvShow convertFrom(TvShowTrakt tvShowTrakt) {
		TvShow newShow = new TvShow();
		newShow.setTitle(tvShowTrakt.getTitle());
		newShow.setDescription(tvShowTrakt.getOverview());
		newShow.setStatus(tvShowTrakt.getStatus());
		newShow.setYear(tvShowTrakt.getYear());
		newShow.setTraktId(tvShowTrakt.getIds().get("trakt"));
		newShow.setImdbId(tvShowTrakt.getIds().getOrDefault("imdb", ""));
		newShow.setSeasons(new ArrayList<>());
		newShow.setEpisodes(new ArrayList<>());
		newShow.setLastLocalUpdate(Timestamp.from(Instant.now()));
		return newShow;
	}
}
