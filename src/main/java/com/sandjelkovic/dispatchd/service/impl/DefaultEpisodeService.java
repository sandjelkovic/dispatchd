package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.Episode;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.repositories.EpisodeRepository;
import com.sandjelkovic.dispatchd.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultEpisodeService implements EpisodeService {

	@Autowired
	private EpisodeRepository repository;

	@Override
	public List<Episode> save(Iterable<Episode> episodeList) {
		return repository.save(episodeList);
	}

	@Override
	public Episode save(Episode episode) {
		return repository.save(episode);
	}

	public Page<Episode> findByTvShow(TvShow show, Pageable pageable) {
		return repository.findByTvShow(show, pageable);
	}

	public Optional<Episode> findOne(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}
}
