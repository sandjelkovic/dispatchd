package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.entities.UserFollowingTvShow;
import com.sandjelkovic.dispatchd.data.entities.UserFollowingTvShowPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowingTvShowRepository extends JpaRepository<UserFollowingTvShow, UserFollowingTvShowPK>{
	List<UserFollowingTvShow> findByUserAndTvShow(User user, TvShow tvShow);
	UserFollowingTvShow findOneByUserAndTvShow(User user, TvShow tvShow);
}
