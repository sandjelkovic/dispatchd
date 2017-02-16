package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.api.dto.EpisodeDTO;
import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class Episode2DTOConverter implements Converter<Episode, EpisodeDTO> {

	@Autowired
	private MapperFacade mapperFacade;

	@Override
	public EpisodeDTO convert(Episode source) {
		EpisodeDTO dto = mapperFacade.map(source, EpisodeDTO.class);
		dto.seasonId(source.getSeason().getId())
				.tvShowId(source.getTvShow().getId());
		return dto;
	}
}
