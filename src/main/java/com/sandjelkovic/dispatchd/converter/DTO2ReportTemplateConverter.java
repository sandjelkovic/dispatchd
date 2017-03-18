package com.sandjelkovic.dispatchd.converter;


import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.dto.TvShowDto;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShowPK;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import com.sandjelkovic.dispatchd.domain.service.TvShowService;
import com.sandjelkovic.dispatchd.helper.EmptyCollections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component
public class DTO2ReportTemplateConverter implements Converter<ReportTemplateDTO, ReportTemplate> {

	@Autowired
	private TvShowService tvShowService;

	@Autowired
	private ReportFacade reportFacade;

	@Override
	public ReportTemplate convert(ReportTemplateDTO source) {
		ReportTemplate destination = new ReportTemplate();
		destination.id(source.getId())
				.active(source.getActive())
				.description(source.getDescription())
				.name(source.getName())
				.timeOfDayToDeliver(source.getTimeOfDayToDeliver())
				.repeatDayOfMonth(source.getRepeatDayOfMonth())
				.repeatDayOfWeek(source.getRepeatDayOfWeek())
				.repeatType(source.getRepeatType())
				.reportTemplate2TvShows(getConvertedTvShows(source));
		return destination;
	}

	private List<ReportTemplate2TvShow> getConvertedTvShows(ReportTemplateDTO source) {
		List<TvShow> collected = Optional.ofNullable(source.getTvShows())
				.orElseGet(EmptyCollections::list).stream()
				.map(TvShowDto::getId)
				.map(tvShowService::findOne)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(toList());

		List<ReportTemplate2TvShow> sorted = EmptyCollections.list();
		for (int i = 0; i < collected.size(); i++) {
			ReportTemplate2TvShow current = new ReportTemplate2TvShow();
			current.setId(new ReportTemplate2TvShowPK());
			current.getId().setShowId(collected.get(i).getId());
			current.getId().setReporttemplateId(source.getId());
			current.setOrderInReport(i);
			sorted.add(current);
		}
		return sorted;
	}
}
