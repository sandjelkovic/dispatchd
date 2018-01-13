package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TvShowService {
	TvShow save(TvShow tvShow);

	Optional<TvShow> findById(Long id);

	Optional<TvShow> findByTraktId(String traktId);

	List<TvShow> findByTitle(String title);

	Page<TvShow> findAll(Pageable pageable);

	Page<TvShow> findByTitleContaining(String title, Pageable pageable);
}
