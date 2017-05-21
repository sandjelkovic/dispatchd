package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.content.trakt.importer.service.impl.DefaultTraktImporterService;
import com.sandjelkovic.dispatchd.domain.data.entity.Episode;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.facade.report.BaseIntegrationTest;
import com.sandjelkovic.dispatchd.testutils.exceptions.DataNotSetupException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.sandjelkovic.dispatchd.config.Constants.STAR_TREK_TNG_SLUG;

/**
 * @author sandjelkovic
 * @date 4.5.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {Constants.SPRING_PROFILE_TESTING})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ContentServiceTest extends BaseIntegrationTest {

	@Autowired
	private DefaultTraktImporterService importerService;

	@Autowired
	private ContentService contentService;

	@Before
	public void setUp() throws Exception {
		super.setUpUsers();
		TvShow sttng = importerService.importShow(STAR_TREK_TNG_SLUG);
		refreshJPAContext();
	}

	@Test
	public void findEpisodeById() throws Exception {
		TvShow tng = showService.findByTitle("Star Trek: The Next Generation")
				.stream()
				.findAny()
				.orElseThrow(DataNotSetupException::new);
		Episode episodeToTest = tng.getEpisodes().get(10);
		long idForTesting = episodeToTest.getId();
		refreshJPAContext(); // just in case.

		Episode episodeById = contentService.findEpisodeById(idForTesting);

		MatcherAssert.assertThat(episodeById, Matchers.notNullValue());
		MatcherAssert.assertThat(episodeById.getTvShow().getId(), Matchers.is(tng.getId()));
		MatcherAssert.assertThat(episodeById.getNumber(), Matchers.is(episodeToTest.getNumber()));
	}

	@Test
	public void findEpisodeListByShow() throws Exception {

	}

	@Test
	public void findEpisodeListBySeason() throws Exception {

	}

	@Test
	public void findShow() throws Exception {

	}

	@Test
	public void findShowByTitleContaining() throws Exception {

	}

	@Test
	public void findShows() throws Exception {

	}

}
