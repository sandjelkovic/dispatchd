package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.Season;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long>{

	List<Season> findByTvShow(TvShow show);
}
