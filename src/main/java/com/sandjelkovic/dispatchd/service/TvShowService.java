package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TvShowService {
	TvShow save(TvShow tvShow);

	Optional<TvShow> get(Long id);

	List<TvShow> findByTraktId(String traktId);

	List<TvShow> findByTitle(String title);

	Page<TvShow> findAll(Pageable pageable);

	List<TvShow> findAll();
}
