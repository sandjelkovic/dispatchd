package com.sandjelkovic.dispatchd.content.trakt.converter;

import com.sandjelkovic.dispatchd.content.trakt.dto.TvShowTrakt;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
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
