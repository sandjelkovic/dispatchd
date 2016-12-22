package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
	List<Episode> findByTvShow(TvShow show);
}
