package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.Season;

import java.util.List;
import java.util.Optional;

public interface SeasonService {
	List<Season> save(Iterable<Season> seasons);

	Season save(Season season);

	Optional<Season> findById(Long id);
}
