package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TvShowRepository extends JpaRepository<TvShow, Long>{
	public List<TvShow> findByTraktId(String traktId);
	public List<TvShow> findByTitle(String title);
}
