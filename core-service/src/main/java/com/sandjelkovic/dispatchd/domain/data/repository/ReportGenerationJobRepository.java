package com.sandjelkovic.dispatchd.domain.data.repository;


import com.sandjelkovic.dispatchd.domain.data.entity.ReportGenerationJob;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportGenerationJob.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.List;

public interface ReportGenerationJobRepository extends JpaRepository<ReportGenerationJob, Long> {
	public List<ReportGenerationJob> findByJobStatusAndTimeToFire(JobStatus jobStatus, ZonedDateTime timeToFire);
}
