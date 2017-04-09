package com.sandjelkovic.dispatchd.event.listener;

import com.sandjelkovic.dispatchd.event.ImportMovieEvent;
import com.sandjelkovic.dispatchd.event.ImportTvShowEvent;

public interface ImportEventListener {
	void handleImportMovie(ImportMovieEvent event);

	void handleImportShow(ImportTvShowEvent event);
}
