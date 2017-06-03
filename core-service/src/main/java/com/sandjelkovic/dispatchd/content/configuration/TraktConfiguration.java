package com.sandjelkovic.dispatchd.content.configuration;

import com.sandjelkovic.dispatchd.content.configuration.interceptor.HeaderRequestInterceptor;
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2EpisodeConverter;
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2SeasonConverter;
import com.sandjelkovic.dispatchd.content.trakt.converter.Trakt2TvShowConverter;
import com.sandjelkovic.dispatchd.content.trakt.provider.TraktMediaProvider;
import com.sandjelkovic.dispatchd.content.trakt.provider.impl.DefaultTraktMediaProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

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

	@Bean
	public Trakt2EpisodeConverter trakt2EpisodeConverter() {
		return new Trakt2EpisodeConverter();
	}

	@Bean
	public Trakt2SeasonConverter trakt2SeasonConverter() {
		return new Trakt2SeasonConverter();
	}

	@Bean
	public Trakt2TvShowConverter trakt2TvShowConverter() {
		return new Trakt2TvShowConverter();
	}

	@Bean
	public TraktMediaProvider defaultTraktMediaProvider(TraktConfiguration traktConfiguration) {
		return new DefaultTraktMediaProvider(getTraktRestTemplate(), traktConfiguration);
	}

	@Bean(name = "traktRestTemplate")
	public RestTemplate getTraktRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Content-type", "application/json"));
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("trakt-api-key", this.getAppId()));
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("trakt-api-version", this.getApiVersion()));
		return restTemplate;
	}

}
