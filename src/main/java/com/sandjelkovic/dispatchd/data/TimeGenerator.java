package com.sandjelkovic.dispatchd.data;


import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;

public class TimeGenerator {

	public ZonedDateTime generateNewGenerationTimeForTemplate(ReportTemplate reportTemplate) {
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime incrementedForRepeatInterval = now;
		if (ReportRepeatType.WEEKLY.equals(reportTemplate.getRepeatType())) {
			incrementedForRepeatInterval = now.plusWeeks(1);
		} else if (ReportRepeatType.MONTHLY.equals(reportTemplate.getRepeatType())) {
			incrementedForRepeatInterval = now.plusMonths(1).withDayOfMonth(reportTemplate.getRepeatDayOfMonth());
		}
		LocalTime timeToDeliver = Optional.ofNullable(reportTemplate.getTimeOfDayToDeliver())
				.orElse(LocalTime.NOON);
		ZonedDateTime resultTime = incrementedForRepeatInterval.withHour(timeToDeliver.getHour())
				.withMinute(timeToDeliver.getMinute())
				.withSecond(0);
		return resultTime;
	}
}
