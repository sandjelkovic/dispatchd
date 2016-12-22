package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.data.entities.TvShow;

public class TvShowImportedEvent {
	public final TvShow eventInfo;

	public TvShowImportedEvent(TvShow tvShow) {
		this.eventInfo = tvShow;
	}
}
