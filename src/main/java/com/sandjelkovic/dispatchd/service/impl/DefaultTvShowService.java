package com.sandjelkovic.dispatchd.service.impl;

import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.repositories.TvShowRepository;
import com.sandjelkovic.dispatchd.service.TvShowService;
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
	public Optional<TvShow> get(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	@Override
	public List<TvShow> findByTraktId(String traktId) {
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
	public List<TvShow> findAll() {
		return repository.findAll();
	}
}
