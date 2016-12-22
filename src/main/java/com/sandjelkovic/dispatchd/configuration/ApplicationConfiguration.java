package com.sandjelkovic.dispatchd.configuration;

import com.sandjelkovic.dispatchd.data.TimeGenerator;
import com.sandjelkovic.dispatchd.interceptor.HeaderRequestInterceptor;
import com.sandjelkovic.dispatchd.orika.converter.LocalDateConverter;
import com.sandjelkovic.dispatchd.orika.converter.ZonedDateTimeConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableScheduling
//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ApplicationConfiguration {

	@Autowired
	private TraktConfiguration traktConfiguration;

	@Bean(name = "traktRestTemplate")
	public RestTemplate getTraktRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("Content-type", "application/json"));
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("trakt-api-key", traktConfiguration.getAppId()));
		restTemplate.getInterceptors().add(new HeaderRequestInterceptor("trakt-api-version", traktConfiguration.getApiVersion()));
		return restTemplate;
	}

	@Bean
	public TimeGenerator timeGenerator() {
		return new TimeGenerator();
	}

	@Bean
	public MapperFactory mapperFactory() {
		DefaultMapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.getConverterFactory().registerConverter(new ZonedDateTimeConverter());
		mapperFactory.getConverterFactory().registerConverter(new LocalDateConverter());
		return mapperFactory;
	}

	@Bean
	public MapperFacade mapperFacade(MapperFactory mapperFactory) {
		return mapperFactory.getMapperFacade();
	}
}
