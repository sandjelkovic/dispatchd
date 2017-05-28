package com.sandjelkovic.dispatchd.domain.facade.report;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.content.trakt.importer.service.impl.DefaultTraktImporterService;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate2TvShow;
import com.sandjelkovic.dispatchd.domain.data.entity.TvShow;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import com.sandjelkovic.dispatchd.exception.ReportTemplateNotFoundException;
import com.sandjelkovic.dispatchd.exception.ShowNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static com.sandjelkovic.dispatchd.config.Constants.AGENTS_OF_SHIELD_SLUG;
import static com.sandjelkovic.dispatchd.config.Constants.STAR_TREK_TNG_SLUG;
import static org.hamcrest.Matchers.*;

/**
 * @author sandjelkovic
 * @date 1.4.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ReportFacadeRelationsIntegrationTest extends BaseIntegrationTest {

	@Autowired
	private ReportFacade target;

	@Autowired
	private DefaultTraktImporterService importerService;

	private TvShow tngShow;
	private TvShow shieldShow;

	@Before
	public void setUp() throws Exception {
		setUpUsers();
		tngShow = importerService.importShow(STAR_TREK_TNG_SLUG);
		shieldShow = importerService.importShow(AGENTS_OF_SHIELD_SLUG);
		refreshJPAContext();
	}

	@Test
	public void disconnectAllShows() throws Exception {

	}

	@Test
	public void disconnectShow() throws Exception {

	}

	@Test
	@WithMockUser(username = USER_NAME)
	public void connectShowHappyCase() throws Exception {
		ReportTemplate template = reportTemplateRepository.save(generateTemplateWithGenerationInPastWithoutShows().user(getUser(USER_NAME)));

		refreshJPAContext();
		target.connectShow(template.getId(), tngShow.getId(), 2);
		refreshJPAContext();

		ReportTemplate templateAfter = reportTemplateRepository.findOne(template.getId());
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), notNullValue());
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), not(empty()));
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), hasSize(1));

		ReportTemplate2TvShow relation = templateAfter.getReportTemplate2TvShows().get(0);
		Assert.assertThat(relation, notNullValue());
		Assert.assertThat(relation.getTvShow().getId(), equalTo(tngShow.getId()));
		Assert.assertThat(relation.getOrderInReport(), equalTo(2));
	}

	@Test(expected = ReportTemplateNotFoundException.class)
	@WithMockUser(username = USER_NAME)
	public void connectShowNoExistingTemplate() throws Exception {
		target.connectShow(9999L, tngShow.getId(), 2);
	}

	@Test(expected = ShowNotFoundException.class)
	@WithMockUser(username = USER_NAME)
	public void connectShowNoExistingShow() throws Exception {
		ReportTemplate template = reportTemplateRepository.save(generateTemplateWithGenerationInPastWithoutShows().user(getUser(USER_NAME)));

		target.connectShow(template.getId(), 9999L, 2);
	}

	@Test
	@WithMockUser(username = USER_NAME)
	public void connectShowMoreShowsSequentially() throws Exception {
		ReportTemplate template = reportTemplateRepository.save(generateTemplateWithGenerationInPastWithoutShows().user(getUser(USER_NAME)));

		refreshJPAContext();
		target.connectShow(template.getId(), tngShow.getId(), 2);
		refreshJPAContext();
		target.connectShow(template.getId(), shieldShow.getId(), 5);

		refreshJPAContext();
		ReportTemplate templateAfter = reportTemplateRepository.findOne(template.getId());

		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), notNullValue());
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), not(empty()));
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), hasSize(2));

		ReportTemplate2TvShow tngRelation = templateAfter.getReportTemplate2TvShows().get(0);
		Assert.assertThat(tngRelation, notNullValue());
		Assert.assertThat(tngRelation.getTvShow().getId(), equalTo(tngShow.getId()));
		Assert.assertThat(tngRelation.getOrderInReport(), equalTo(2));

		ReportTemplate2TvShow shieldRelation = templateAfter.getReportTemplate2TvShows().get(1);
		Assert.assertThat(shieldRelation, notNullValue());
		Assert.assertThat(shieldRelation.getTvShow().getId(), equalTo(shieldShow.getId()));
		Assert.assertThat(shieldRelation.getOrderInReport(), equalTo(5));
	}

	@WithMockUser(username = USER_NAME)
	public void connectShowTwiceSameShow() throws Exception {
		ReportTemplate template = reportTemplateRepository.save(generateTemplateWithGenerationInPastWithoutShows().user(getUser(USER_NAME)));

		target.connectShow(template.getId(), tngShow.getId(), 2);
		refreshJPAContext();
		target.connectShow(template.getId(), tngShow.getId(), 5);
		refreshJPAContext();

		ReportTemplate templateAfter = reportTemplateRepository.findOne(template.getId());
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), notNullValue());
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), not(empty()));
		// should have just updated
		Assert.assertThat(templateAfter.getReportTemplate2TvShows(), hasSize(1));

		ReportTemplate2TvShow relation = templateAfter.getReportTemplate2TvShows().get(0);
		Assert.assertThat(relation, notNullValue());
		Assert.assertThat(relation.getTvShow().getId(), equalTo(tngShow.getId()));
		// should be updated value
		Assert.assertThat(relation.getOrderInReport(), equalTo(5));
	}

}
