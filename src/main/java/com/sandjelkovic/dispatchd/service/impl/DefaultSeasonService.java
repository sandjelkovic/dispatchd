package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.repositories.SeasonRepository;
import com.sandjelkovic.dispatchd.service.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultSeasonService implements SeasonService {

	@Autowired
	private SeasonRepository repository;


	@Override
	public List<Season> save(Iterable<Season> seasons) {
		return repository.save(seasons);
	}

	@Override
	public Season save(Season season) {
		return repository.save(season);
	}
}
