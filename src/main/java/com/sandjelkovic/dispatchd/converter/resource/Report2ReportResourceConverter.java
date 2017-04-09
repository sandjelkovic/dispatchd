package com.sandjelkovic.dispatchd.converter.resource;

import com.sandjelkovic.dispatchd.converter.Episode2DTOConverter;
import com.sandjelkovic.dispatchd.converter.GeneratedReport2DTOConverter;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReport;
import com.sandjelkovic.dispatchd.domain.data.entity.GeneratedReportContent;
import com.sandjelkovic.dispatchd.gateway.api.dto.ReportDTO;
import com.sandjelkovic.dispatchd.gateway.api.resource.ReportResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class Report2ReportResourceConverter implements Converter<GeneratedReport, ReportResource> {

	@Autowired
	private GeneratedReport2DTOConverter dtoConverter;

	@Autowired
	private Episode2DTOConverter episode2DTOConverter;

	@Override
	public ReportResource convert(GeneratedReport source) {
		ReportDTO dto = dtoConverter.convert(source);
		dto.setContent(source.getGeneratedReportContents().stream()
				.sorted(Comparator.comparingInt(GeneratedReportContent::getOrderInReport))
				.map(GeneratedReportContent::getEpisode)
				.map(episode2DTOConverter::convert)
				.collect(Collectors.toList()));
		return new ReportResource(dto);
	}
}
