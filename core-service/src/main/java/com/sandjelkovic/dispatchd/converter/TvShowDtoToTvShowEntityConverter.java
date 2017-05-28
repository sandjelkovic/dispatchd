package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.common.helper.ChronoHelper;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TvShowDtoToTvShowEntityConverter implements Converter<TvShowDTO, TvShow> {
	@Override
	public TvShow convert(TvShowDTO source) {
		TvShow tvShow = new TvShow();
		tvShow.setId(source.getId());
		tvShow.setStatus(source.getStatus());
		tvShow.setTitle(source.getTitle());
		tvShow.setTraktId(source.getTraktId());
		tvShow.setImdbId(source.getImdbId());
		tvShow.setTmdbId(source.getTmdbId());
		tvShow.setTvdbId(source.getTvdbId());
		tvShow.setYear(source.getYear());
		tvShow.setLastLocalUpdate(ChronoHelper.timestampFromNullable(source.getLastUpdatedAt().toInstant()));
		return tvShow;
	}
}
