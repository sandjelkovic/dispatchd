package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.data.entity.UserFollowingTvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.UserFollowingTvShowPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFollowingTvShowRepository extends JpaRepository<UserFollowingTvShow, UserFollowingTvShowPK>{
	List<UserFollowingTvShow> findByUserAndTvShow(User user, TvShow tvShow);
	UserFollowingTvShow findOneByUserAndTvShow(User user, TvShow tvShow);
}
