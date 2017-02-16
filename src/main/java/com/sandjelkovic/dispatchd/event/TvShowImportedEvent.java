package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;

public class TvShowImportedEvent {
	public final TvShow eventInfo;

	public TvShowImportedEvent(TvShow tvShow) {
		this.eventInfo = tvShow;
	}
}
