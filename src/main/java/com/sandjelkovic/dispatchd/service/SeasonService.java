package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.Season;

import java.util.List;
import java.util.Optional;

public interface SeasonService {
	List<Season> save(Iterable<Season> seasons);

	Season save(Season season);

	Optional<Season> findOne(Long id);
}
