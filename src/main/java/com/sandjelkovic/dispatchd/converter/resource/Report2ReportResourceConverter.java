package com.sandjelkovic.dispatchd.converter.resource;

import com.sandjelkovic.dispatchd.api.resources.ReportDTOResource;
import com.sandjelkovic.dispatchd.converter.Episode2DTOConverter;
import com.sandjelkovic.dispatchd.converter.GeneratedReport2DTOConverter;
import com.sandjelkovic.dispatchd.data.dto.ReportDTO;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;
import com.sandjelkovic.dispatchd.data.entities.GeneratedReportContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class Report2ReportResourceConverter implements Converter<GeneratedReport, ReportDTOResource> {

	@Autowired
	private GeneratedReport2DTOConverter dtoConverter;

	@Autowired
	private Episode2DTOConverter episode2DTOConverter;

	@Override
	public ReportDTOResource convert(GeneratedReport source) {
		ReportDTO dto = dtoConverter.convert(source);
		dto.setContent(source.getGeneratedReportContents().stream()
				.sorted(Comparator.comparingInt(GeneratedReportContent::getOrderInReport))
				.map(GeneratedReportContent::getEpisode)
				.map(episode2DTOConverter::convert)
				.collect(Collectors.toList()));
		return new ReportDTOResource(dto);
	}
}
