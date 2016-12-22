package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.data.dto.TvShowDto;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.helper.ChronoHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TvShowDtoToTvShowEntityConverter implements Converter<TvShowDto, TvShow> {
	@Override
	public TvShow convert(TvShowDto source) {
		TvShow tvShow = new TvShow();
		tvShow.setId(source.getId());
		tvShow.setStatus(source.getStatus());
		tvShow.setTitle(source.getTitle());
		tvShow.setTraktId(source.getIds().getOrDefault("trakt", ""));
		tvShow.setImdbId(source.getIds().getOrDefault("imdb", ""));
		tvShow.setTmdbId(source.getIds().getOrDefault("tmdb", ""));
		tvShow.setTvdbId(source.getIds().getOrDefault("tvdb", ""));
		tvShow.setYear(source.getYear());
		tvShow.setLastLocalUpdate(ChronoHelper.timestampFromNullable(source.getLastUpdatedAt()));
		return tvShow;
	}
}
