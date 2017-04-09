package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EpisodeRepository extends CrudRepository<Episode, Long> {
	Page<Episode> findByTvShow(TvShow show, Pageable pageable);

	List<Episode> findByTvShow(TvShow show);

	Page<Episode> findBySeason(Season season, Pageable pageable);

	List<Episode> findBySeason(Season season);
}
