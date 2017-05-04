package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface TvShowRepository extends PagingAndSortingRepository<TvShow, Long> {
	Optional<TvShow> findByTraktId(String traktId);

	List<TvShow> findByTitle(String title);

	Page<TvShow> findByTitleContaining(String title, Pageable pageable);
}
