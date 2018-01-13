package com.sandjelkovic.dispatchd.domain.service.impl;

import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.repository.TvShowRepository;
import com.sandjelkovic.dispatchd.domain.service.TvShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultTvShowService implements TvShowService {

	@Autowired
	private TvShowRepository repository;

	@Override
	public TvShow save(TvShow tvShow) {
		tvShow.setLastLocalUpdate(Timestamp.from(Instant.now()));
		return repository.save(tvShow);
	}

	@Override
	public Optional<TvShow> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Optional<TvShow> findByTraktId(String traktId) {
		return repository.findByTraktId(traktId);
	}

	@Override
	public List<TvShow> findByTitle(String title) {
		return repository.findByTitle(title);
	}

	@Override
	public Page<TvShow> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Page<TvShow> findByTitleContaining(String title, Pageable pageable) {
		return repository.findByTitleContaining(title, pageable);
	}
}
