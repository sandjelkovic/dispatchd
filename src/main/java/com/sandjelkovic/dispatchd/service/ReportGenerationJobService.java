package com.sandjelkovic.dispatchd.service;

import com.sandjelkovic.dispatchd.data.entities.ReportGenerationJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface ReportGenerationJobService {

	void delete(Iterable<ReportGenerationJob> entities);

	void delete(ReportGenerationJob entity);

	void delete(Long id);

	ReportGenerationJob save(ReportGenerationJob entity);

	ReportGenerationJob findOne(Long id);

	List<ReportGenerationJob> findAll();

	List<ReportGenerationJob> save(Iterable<ReportGenerationJob> entities);

	Page<ReportGenerationJob> findAll(Pageable pageable);

	boolean exists(Long id);

	List<ReportGenerationJob> modifyAndSave(List<ReportGenerationJob> jobs, Consumer<ReportGenerationJob> operation);

	List<ReportGenerationJob> getNotStartedJobsUntil(ZonedDateTime time);

	List<ReportGenerationJob> changeStatusOfJobs(ReportGenerationJob.JobStatus inProgress, List<ReportGenerationJob> jobs);
}
