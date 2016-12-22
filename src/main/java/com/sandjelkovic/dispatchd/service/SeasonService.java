package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.Season;

import java.util.List;

public interface SeasonService {
	List<Season> save(Iterable<Season> seasons);

	Season save(Season season);
}
