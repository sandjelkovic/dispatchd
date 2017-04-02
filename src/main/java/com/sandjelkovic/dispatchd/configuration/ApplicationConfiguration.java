package com.sandjelkovic.dispatchd.configuration;

import com.sandjelkovic.dispatchd.domain.data.TimeGenerator;
import com.sandjelkovic.dispatchd.helper.DefaultEventDispatcher;
import com.sandjelkovic.dispatchd.orika.converter.LocalDateConverter;
import com.sandjelkovic.dispatchd.orika.converter.ZonedDateTimeConverter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ResourceProcessorInvoker;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collection;

@Configuration
@EnableAsync
@EnableScheduling
//@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ApplicationConfiguration {

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

	@Bean
	public DefaultEventDispatcher defaultEventDispatcher(ApplicationEventPublisher publisher) {
		return new DefaultEventDispatcher(publisher);
	}

	@Bean
	public ResourceProcessorInvoker resourceProcessorInvoker(Collection<ResourceProcessor<?>> processors) {
		return new ResourceProcessorInvoker(processors);
	}
}
