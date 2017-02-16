package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.helper.EmptyCollections;
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
