package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.UpdateJob;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UpdateJobRepository extends CrudRepository<UpdateJob, Long> {
	Optional<UpdateJob> findFirstByOrderByFinishTimeDesc();

	Optional<UpdateJob> findFirstBySuccessOrderByFinishTimeDesc(boolean success);
}
