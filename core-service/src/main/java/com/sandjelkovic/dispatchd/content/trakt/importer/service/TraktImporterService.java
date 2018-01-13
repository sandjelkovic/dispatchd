package com.sandjelkovic.dispatchd.content.trakt.importer.service;

import com.sandjelkovic.dispatchd.domain.data.entity.ImportStatus;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.List;
import java.util.Optional;

public interface TraktImporterService {

	TvShow importShow(List<String> segments);

	TvShow importShow(String showId);

	@Async
	AsyncResult<TvShow> importShowAsync(String showId);

	ImportStatus createNewImportStatus(List<String> segments);

	ImportStatus save(ImportStatus status);

	Optional<ImportStatus> findImportStatus(Long id);
}
