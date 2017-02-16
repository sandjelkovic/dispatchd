package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;

public class TvShowRefreshedEvent {
	public final TvShow eventInfo;

	public TvShowRefreshedEvent(TvShow eventInfo) {
		this.eventInfo = eventInfo;
	}
}
