package com.sandjelkovic.dispatchd.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.helpers.TestDataGenerator;
import com.sandjelkovic.dispatchd.testutils.mock.MockingTraktMediaProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.TestPropertySource;

@Configuration
@TestPropertySource(locations = "application-testing.properties")
@Profile(Constants.SPRING_PROFILE_TESTING)
public class TestConfiguration {

	@Bean(destroyMethod = "shutdown")
	public EmbeddedDatabase dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.build();
	}

	@Primary
	@Bean
	public MockingTraktMediaProvider mockingTraktMediaProvider(ObjectMapper objectMapper) {
		return new MockingTraktMediaProvider(objectMapper);
	}
	@Bean
	public TestDataGenerator testDataGenerator() {
		return new TestDataGenerator();
	}
}
