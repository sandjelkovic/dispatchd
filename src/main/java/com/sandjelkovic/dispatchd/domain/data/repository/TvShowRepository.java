package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvShowRepository extends JpaRepository<TvShow, Long>{
	public List<TvShow> findByTraktId(String traktId);
	public List<TvShow> findByTitle(String title);
}
