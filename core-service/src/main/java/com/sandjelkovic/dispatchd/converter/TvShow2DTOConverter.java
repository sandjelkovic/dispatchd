package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TvShow2DTOConverter implements Converter<TvShow, TvShowDTO> {
	@Autowired
	private MapperFacade mapperFacade;

	@Override
	public TvShowDTO convert(TvShow source) {
		return mapperFacade.map(source, TvShowDTO.class);
	}
}
