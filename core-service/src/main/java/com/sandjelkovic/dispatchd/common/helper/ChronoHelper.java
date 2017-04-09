package com.sandjelkovic.dispatchd.common.helper;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

public class ChronoHelper {
	public static Instant defaultIfNull(Instant instant, Instant defaultValue) {
		if (instant == null) {
			return defaultValue;
		}
		return instant;
	}

	public static Timestamp timestampFromNullable(Instant instant) {
		return Optional.ofNullable(instant)
				.map(Timestamp::from)
				.orElse(null);
	}
}
