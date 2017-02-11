package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.repositories.EpisodeRepository;
import com.sandjelkovic.dispatchd.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultEpisodeService implements EpisodeService {

	@Autowired
	private EpisodeRepository repository;

	@Override
	public Iterable<Episode> save(Iterable<Episode> episodeList) {
		return repository.save(episodeList);
	}

	@Override
	public Episode save(Episode episode) {
		return repository.save(episode);
	}

	@Override
	public Page<Episode> findByTvShow(TvShow show, Pageable pageable) {
		return repository.findByTvShow(show, pageable);
	}

	@Override
	public Optional<Episode> findOne(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	@Override
	public Page<Episode> findBySeason(Season season, Pageable pageable) {
		return repository.findBySeason(season, pageable);
	}
}
