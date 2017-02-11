package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
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
