package com.sandjelkovic.dispatchd.domain.service.impl;

import com.sandjelkovic.dispatchd.domain.data.entity.ReportGenerationJob;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportGenerationJob.JobStatus;
import com.sandjelkovic.dispatchd.domain.data.repository.ReportGenerationJobRepository;
import com.sandjelkovic.dispatchd.domain.service.ReportGenerationJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

@Component
public class DefaultReportGenerationJobService implements ReportGenerationJobService {

	@Autowired
	private ReportGenerationJobRepository jobRepository;

	public void delete(Iterable<ReportGenerationJob> entities) {
		jobRepository.delete(entities);
	}

	public void delete(ReportGenerationJob entity) {
		jobRepository.delete(entity);
	}

	public void delete(Long id) {
		jobRepository.delete(id);
	}

	public ReportGenerationJob save(ReportGenerationJob entity) {
		return jobRepository.save(entity);
	}

	public ReportGenerationJob findOne(Long id) {
		return jobRepository.findOne(id);
	}

	public List<ReportGenerationJob> findAll() {
		return jobRepository.findAll();
	}

	public List<ReportGenerationJob> save(Iterable<ReportGenerationJob> entities) {
		return jobRepository.save(entities);
	}

	public Page<ReportGenerationJob> findAll(Pageable pageable) {
		return jobRepository.findAll(pageable);
	}

	public boolean exists(Long id) {
		return jobRepository.exists(id);
	}

	@Override
	public List<ReportGenerationJob> modifyAndSave(List<ReportGenerationJob> jobs, Consumer<ReportGenerationJob> operation) {
		jobs.stream()
				.forEach(operation::accept);
		return jobRepository.save(jobs);
	}

	@Override
	public List<ReportGenerationJob> getNotStartedJobsUntil(ZonedDateTime time) {
		return jobRepository.findByJobStatusAndTimeToFire(JobStatus.NOT_STARTED, time);
	}

	@Override
	public List<ReportGenerationJob> changeStatusOfJobs(JobStatus status, List<ReportGenerationJob> jobs) {
		return modifyAndSave(jobs, (job) -> job.setJobStatus(status));
	}
}
