package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;
import com.sandjelkovic.dispatchd.gateway.api.dto.ImportStatusDTO;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ImportStatus2ImportStatusDtoConverter implements Converter<ImportStatus, ImportStatusDTO> {

	@Autowired
	private MapperFacade mapperFacade;

	@Override
	public ImportStatusDTO convert(ImportStatus source) {
		return mapperFacade.map(source, ImportStatusDTO.class);
	}
}
