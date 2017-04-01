package com.sandjelkovic.dispatchd.domain.facade.report;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author ${sandjelkovic}
 * @date 13.1.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ReportFacadeTemplateSecurityTest extends BaseReportFacadeTest {

	@Autowired
	private ReportFacade target;

	@Before
	public void setUp() {
		setUpData();
	}

	@Test(expected = AccessDeniedException.class)
	@WithMockUser(username = USER_NAME, password = USER_PASSWORD, roles = {"USER"})
	public void findExistingTemplateOfDifferentUser() throws Exception {
		User userTwo = getUser(USER_TWO_NAME);
		ReportTemplate reportTemplate = generateTemplateWithGenerationInFutureWithoutShows().user(userTwo);
		reportTemplate = reportTemplateRepository.save(reportTemplate);

		Optional<ReportTemplate> foundTemplate = target.findTemplate(reportTemplate.getId());
	}

}
