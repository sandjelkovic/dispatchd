package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.TvShow;

import java.util.List;
import java.util.Optional;

public interface EpisodeService {
	List<Episode> save(Iterable<Episode> episodeList);

	Episode save(Episode episode);

	List<Episode> findByTvShow(TvShow show);

	Optional<Episode> findOne(Long id);
}
