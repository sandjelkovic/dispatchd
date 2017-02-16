package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;
import org.springframework.stereotype.Component;

@Component
public class SeasonEntityConverter {

	public Season convertFrom(SeasonTrakt seasonTrakt) {
		Season season = new Season();
		season.setNumber(seasonTrakt.getNumber());
		season.setDescription(seasonTrakt.getOverview());
		season.setAirdate(ChronoHelper.timestampFromNullable(seasonTrakt.getFirstAired()));
		season.setEpisodesCount(seasonTrakt.getEpisodeCount());
		season.setEpisodesAiredCount(seasonTrakt.getAiredEpisodes());
		season.setTraktId(seasonTrakt.getIds().get("trakt"));
		season.setImdbId(seasonTrakt.getIds().getOrDefault("idmb", ""));
		return season;
	}
}
