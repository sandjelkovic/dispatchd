package com.sandjelkovic.dispatchd.domain.service.impl;

import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.repository.SeasonRepository;
import com.sandjelkovic.dispatchd.domain.service.SeasonService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultSeasonService implements SeasonService {

	@Autowired
	private SeasonRepository repository;

	@Override
	public List<Season> save(Iterable<Season> seasons) {
		Iterable<Season> iterable = repository.saveAll(seasons);
		List<Season> savedSeasons = new ArrayList<>();
		CollectionUtils.addAll(savedSeasons, iterable);
		return savedSeasons;
	}

	@Override
	public Season save(Season season) {
		return repository.save(season);
	}

	@Override
	public Optional<Season> findById(Long id) {
		return repository.findById(id);
	}
}
