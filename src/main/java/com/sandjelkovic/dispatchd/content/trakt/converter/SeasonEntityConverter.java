package com.sandjelkovic.dispatchd.content.trakt.converter;

import com.sandjelkovic.dispatchd.content.trakt.dto.SeasonTrakt;
import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SeasonEntityConverter implements Converter<SeasonTrakt, Season> {

	@Override
	public Season convert(SeasonTrakt source) {
		Season season = new Season();
		season.setNumber(source.getNumber());
		season.setDescription(source.getOverview());
		season.setAirdate(ChronoHelper.timestampFromNullable(source.getFirstAired()));
		season.setEpisodesCount(source.getEpisodeCount());
		season.setEpisodesAiredCount(source.getAiredEpisodes());
		season.setTraktId(source.getIds().get("trakt"));
		season.setImdbId(source.getIds().getOrDefault("idmb", ""));
		return season;
	}
}
