package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByEmail(String email);

	Optional<User> findOneByUsername(String username);

	@Modifying
	@Query("update User u set u.enabled =?2 where u.username = ?1")
	int setEnabledFieldForUsername(String username, boolean enabledField);
}
