package com.sandjelkovic.dispatchd.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ${sandjelkovic}
 * @date 5.1.17.
 */
@Configuration
public class ValuesConfiguration {
	@Value(value = "${report.generation.interval.seconds:60}")
	private int generationInterval;

	@Value(value = "${report.max.content.count:150}")
	private int reportMaxTargetsCount;

	public int getGenerationInterval() {
		return generationInterval;
	}

	public int getReportMaxTargetsCount() {
		return reportMaxTargetsCount;
	}
}
