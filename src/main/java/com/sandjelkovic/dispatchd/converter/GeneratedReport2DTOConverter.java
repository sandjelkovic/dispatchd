package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.common.helper.EmptyCollections;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.gateway.api.dto.ReportDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GeneratedReport2DTOConverter implements Converter<GeneratedReport, ReportDTO> {

	@Override
	public ReportDTO convert(GeneratedReport source) {
		return new ReportDTO()
				.id(source.getId())
				.templateId(source.getReportTemplate().getId())
				.text(source.getText())
				.content(EmptyCollections.list());
	}
}
