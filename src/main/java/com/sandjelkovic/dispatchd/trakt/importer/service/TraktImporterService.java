package com.sandjelkovic.dispatchd.trakt.importer.service;

import com.sandjelkovic.dispatchd.data.entities.ImportStatus;
import com.sandjelkovic.dispatchd.data.entities.TvShow;
import com.sandjelkovic.dispatchd.trakt.dto.EpisodeTrakt;
import com.sandjelkovic.dispatchd.trakt.dto.SeasonTrakt;

import java.util.List;
import java.util.concurrent.Future;

public interface TraktImporterService {

	TvShow importShow(List<String> segments);

	ImportStatus createNewImportStatus(List<String> segments);

	ImportStatus save(ImportStatus status);

	ImportStatus findOneImportStatus(Long id);

	Future<List<SeasonTrakt>> getSeasonsFromTraktAsync(String showId);

	Future<List<EpisodeTrakt>> getEpisodesFromTraktAsync(String showId);
}
