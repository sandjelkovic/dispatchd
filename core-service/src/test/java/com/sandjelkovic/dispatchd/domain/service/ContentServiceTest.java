package com.sandjelkovic.dispatchd.domain.service;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.facade.report.BaseIntegrationTest;
import com.sandjelkovic.dispatchd.testutils.exceptions.DataNotSetupException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sandjelkovic
 * @date 4.5.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ContentServiceTest extends BaseIntegrationTest {
	@Before
	public void setUp() throws Exception {
		super.setUpUsers();
		TvShow starTrekTNG = testDataGenerator.createStarTrekTNG();
		Long savedId = fullySaveShow(starTrekTNG);
		refreshJPAContext();
		starTrekTNG = showService.findOne(savedId).orElseThrow(DataNotSetupException::new);
		//todo add episodes to the generator.
	}

	@Test
	public void findEpisodeById() throws Exception {

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
