package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.api.dto.ImportStatusDto;
import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ImportStatus2ImportStatusDtoConverter implements Converter<ImportStatus, ImportStatusDto> {

	@Autowired
	private MapperFacade mapperFacade;

	@Override
	public ImportStatusDto convert(ImportStatus source) {
		return mapperFacade.map(source, ImportStatusDto.class);
	}
}
