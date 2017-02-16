package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EpisodeService {
	Iterable<Episode> save(Iterable<Episode> episodeList);

	Episode save(Episode episode);

	Page<Episode> findByTvShow(TvShow show, Pageable pageable);

	Optional<Episode> findOne(Long id);

	Page<Episode> findBySeason(Season season, Pageable pageable);
}
