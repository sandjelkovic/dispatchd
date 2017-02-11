package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.UpdateJob;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UpdateJobRepository extends CrudRepository<UpdateJob, Long> {
	Optional<UpdateJob> findFirstByOrderByFinishTimeDesc();

	Optional<UpdateJob> findFirstBySuccessOrderByFinishTimeDesc(boolean success);
}
