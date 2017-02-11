package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EpisodeService {
	List<Episode> save(Iterable<Episode> episodeList);

	Episode save(Episode episode);

	Page<Episode> findByTvShow(TvShow show, Pageable pageable);

	Optional<Episode> findOne(Long id);
}
