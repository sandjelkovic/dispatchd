package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentService {
	Episode findEpisodeById(Long episodeId);

	Page<Episode> findEpisodeListByShow(Long showId, Pageable pageable);

	Page<Episode> findEpisodeListBySeason(Long seasonId, Pageable pageable);
}
