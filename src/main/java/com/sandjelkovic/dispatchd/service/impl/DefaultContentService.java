package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.exception.ResourceNotFoundException;
import com.sandjelkovic.dispatchd.service.ContentService;
import com.sandjelkovic.dispatchd.service.EpisodeService;
import com.sandjelkovic.dispatchd.service.SeasonService;
import com.sandjelkovic.dispatchd.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author ${sandjelkovic}
 * @date 28.1.17.
 */
@Service
public class DefaultContentService implements ContentService {

	@Autowired
	private EpisodeService episodeService;
	@Autowired
	private TvShowService tvShowService;
	@Autowired
	private SeasonService seasonService;

	@Override
	public Episode findEpisodeById(Long episodeId) {
		return episodeService.findOne(episodeId)
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public Page<Episode> findEpisodeListByShow(Long showId, Pageable pageable) {
		TvShow show = tvShowService.findOne(showId)
				.orElseThrow(ResourceNotFoundException::new);
		return episodeService.findByTvShow(show, pageable);
	}

	@Override
	public Page<Episode> findEpisodeListBySeason(Long seasonId, Pageable pageable) {
		Season season = seasonService.findOne(seasonId)
				.orElseThrow(ResourceNotFoundException::new);
		return episodeService.findBySeason(season, pageable);
	}
}
