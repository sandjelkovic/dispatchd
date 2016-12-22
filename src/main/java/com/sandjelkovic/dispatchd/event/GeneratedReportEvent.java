package com.sandjelkovic.dispatchd.event;

import com.sandjelkovic.dispatchd.data.entities.GeneratedReport;

public class GeneratedReportEvent {
	public final GeneratedReport eventInfo;

	public GeneratedReportEvent(GeneratedReport data) {
		this.eventInfo = data;
	}
}
