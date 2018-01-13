package com.sandjelkovic.dispatchd.domain.facade.report;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.data.entity.User;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {DispatchdApplication.class})
@Transactional
public class ReportFacadeTemplatesIntegrationTest extends BaseIntegrationTest {

	@Autowired
	private ReportFacade target;

	@Before
	public void setUp() {
		setUpData();
	}

	@Test
	@WithMockUser(username = USER_NAME, password = USER_PASSWORD, roles = {"USER"})
	public void findNotExistingTemplate() {
		Optional<ReportTemplate> template = target.findTemplate(5555L);

		assertThat(template.isPresent(), not(true));
	}

	@Test
	@WithMockUser(username = USER_NAME, password = USER_PASSWORD, roles = {"USER"})
	public void findExistingTemplate() throws Exception {
		User user = getUser(USER_NAME);
		ReportTemplate reportTemplate = generateTemplateWithGenerationInFutureWithoutShows().user(user);
		reportTemplate = reportTemplateRepository.save(reportTemplate);

		Optional<ReportTemplate> foundTemplate = target.findTemplate(reportTemplate.getId());

		assertThat(foundTemplate.isPresent(), is(true));
		//noinspection OptionalGetWithoutIsPresent
		assertThat(foundTemplate.get(), samePropertyValuesAs(reportTemplate));
	}

	@Test
	@WithMockUser(username = USER_NAME, password = USER_PASSWORD, roles = {"USER"})
	public void findTemplatesForUser() throws Exception {
		User user = getUser(USER_NAME);
		int amountToRetrieve = 4;
		long totalAmountToSave = 6;
		List<ReportTemplate> generatedTemplates = generateTemplates(totalAmountToSave).stream()
				.map(template -> template.user(user))
				.map(reportTemplateRepository::save)
				.collect(toList());

		createTemplatesForUserTwo();

		Page<ReportTemplate> pageOfTemplates = target.findTemplatesForUser(generateSimplePageable(0, amountToRetrieve, Sort.unsorted()), USER_NAME);

		assertThat(pageOfTemplates.getTotalElements(), is(totalAmountToSave));
		assertThat(pageOfTemplates.getNumberOfElements(), is(amountToRetrieve));
		assertThat(pageOfTemplates.getTotalPages(), is(2));
		assertThat(pageOfTemplates.getNumber(), is(0));
		assertThat(pageOfTemplates.getContent(), Matchers.hasSize(amountToRetrieve));
	}

	private List<ReportTemplate> createTemplatesForUserTwo() {
		User userTwo = getUser(USER_TWO_NAME);
		return generateTemplates(3).stream()
				.map(template -> template.user(userTwo))
				.map(reportTemplateRepository::save)
				.collect(toList());
	}

}
