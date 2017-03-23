package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class ReportTemplate2DTOConverter implements Converter<ReportTemplate, ReportTemplateDTO> {

	@Autowired
	@Lazy
	private ConversionService conversionService;

	@Override
	public ReportTemplateDTO convert(ReportTemplate source) {
		ReportTemplateDTO dto = new ReportTemplateDTO();
		dto.id(source.getId())
				.active(source.getActive())
				.description(source.getDescription())
				.name(source.getName())
				.timeOfDayToDeliver(source.getTimeOfDayToDeliver())
				.repeatDayOfMonth(source.getRepeatDayOfMonth())
				.repeatDayOfWeek(source.getRepeatDayOfWeek())
				.repeatType(source.getRepeatType())
				.tvShows(source.getReportTemplate2TvShows().stream()
						.sorted((o1, o2) -> o1.getOrderInReport().compareTo(o2.getOrderInReport()))
						.map(ReportTemplate2TvShow::getTvShow)
						.map(tvShow -> conversionService.convert(tvShow, TvShowDTO.class))
						.collect(toList()))
				.username(source.getUser().getUsername());
		return dto;
	}
}
