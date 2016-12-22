package com.sandjelkovic.dispatchd.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/config/application-traktremote.properties")
public class TraktConfiguration {

	@Value("${trakt.baseServiceUrl}")
	private String baseServiceUrl;

	@Value("${trakt.appId}")
	private String appId;

	@Value("${trakt.appSecret}")
	private String appSecret;

	@Value("${trakt.apiVersion}")
	private String apiVersion;

	public String getBaseServiceUrl() {
		return baseServiceUrl;
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getApiVersion() {
		return apiVersion;
	}
}
