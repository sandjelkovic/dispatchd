package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.Season;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long>{

	List<Season> findByTvShow(TvShow show);
}
