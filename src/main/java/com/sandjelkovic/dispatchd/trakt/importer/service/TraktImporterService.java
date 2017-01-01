package com.sandjelkovic.dispatchd.trakt.importer.service;

import com.sandjelkovic.dispatchd.data.entities.ImportStatus;
import com.sandjelkovic.dispatchd.data.entities.TvShow;

import java.util.List;

public interface TraktImporterService {

	TvShow importShow(List<String> segments);

	ImportStatus createNewImportStatus(List<String> segments);

	ImportStatus save(ImportStatus status);

	ImportStatus findOneImportStatus(Long id);
}
