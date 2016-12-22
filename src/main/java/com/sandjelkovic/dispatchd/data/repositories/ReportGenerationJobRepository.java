package com.sandjelkovic.dispatchd.data.repositories;


import com.sandjelkovic.dispatchd.data.entities.ReportGenerationJob;
import com.sandjelkovic.dispatchd.data.entities.ReportGenerationJob.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReportGenerationJobRepository extends JpaRepository<ReportGenerationJob, Long> {
	public List<ReportGenerationJob> findByJobStatusAndTimeToFire(JobStatus jobStatus, ZonedDateTime timeToFire);
}
