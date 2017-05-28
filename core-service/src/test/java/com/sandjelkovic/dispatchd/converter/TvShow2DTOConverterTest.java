package com.sandjelkovic.dispatchd.converter;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.gateway.api.dto.TvShowDTO;
import com.sandjelkovic.dispatchd.helpers.TestDataGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author sandjelkovic
 * @date 28.5.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
// Spring needed for Orika
public class TvShow2DTOConverterTest {
	@Autowired
	private TvShow2DTOConverter converter;

	@Autowired
	private TestDataGenerator generator;

	@Test
	public void convert() throws Exception {
		TvShow source = generator.createStarTrekTNG();

		TvShowDTO result = converter.convert(source);

		assertThat(result, notNullValue());
		assertThat(result.getId(), is(source.getId()));
		assertThat(result.getTitle(), is(source.getTitle()));
		assertThat(result.getDescription(), is(source.getDescription()));
		assertThat(result.getStatus(), is(source.getStatus()));
		assertThat(result.getImdbId(), is(source.getImdbId()));
		assertThat(result.getTvdbId(), is(source.getTvdbId()));
		assertThat(result.getTmdbId(), is(source.getTmdbId()));
		assertThat(result.getTraktId(), is(source.getTraktId()));
		assertThat(result.getYear(), is(source.getYear()));
		ZonedDateTime zonedUpdate = ZonedDateTime.ofInstant(source.getLastLocalUpdate().toInstant(), ZoneId.systemDefault());
		assertThat(result.getLastUpdatedAt(), is(zonedUpdate));
	}
}
