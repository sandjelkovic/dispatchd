package com.sandjelkovic.dispatchd.common.helper;


import java.math.BigInteger;
import java.time.Duration;

public class DomainChronoHelper {
	public static Duration fromUserDelayTime(BigInteger delay) {
		return Duration.ofMinutes(delay.longValue());
	}

	public static Duration fromUserDelayTime(long delay) {
		return Duration.ofMinutes(delay);
	}

	public static long toUserDelayTime(Duration delay) {
		return delay.toMinutes();
	}
}
