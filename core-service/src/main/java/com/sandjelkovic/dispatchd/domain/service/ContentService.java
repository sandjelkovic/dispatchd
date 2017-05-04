package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {
	Episode findEpisodeById(Long episodeId);

	Page<Episode> findEpisodeListByShow(Long showId, Pageable pageable);

	Page<Episode> findEpisodeListBySeason(Long seasonId, Pageable pageable);

	TvShow findShow(Long showId);

	Page<TvShow> findShowByTitleContaining(String title, Pageable pageable);

	Page<TvShow> findShows(Pageable pageable);
}
