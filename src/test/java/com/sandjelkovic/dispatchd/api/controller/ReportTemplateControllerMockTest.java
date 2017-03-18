package com.sandjelkovic.dispatchd.api.controller;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.api.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.api.link.RelNamesConstants;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.domain.data.entity.ReportTemplate;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.sandjelkovic.dispatchd.api.controller.ReportTemplateControllerMockTest.USER_NAME;
import static com.sandjelkovic.dispatchd.api.controller.ReportTemplateControllerMockTest.USER_PASSWORD;
import static com.sandjelkovic.dispatchd.configuration.Constants.REST_ENDPOINT_API_PREFIX;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = {DispatchdApplication.class})
@AutoConfigureMockMvc(secure = true)
@WithMockUser(username = USER_NAME, password = USER_PASSWORD, roles = {"USER"})
@Transactional
public class ReportTemplateControllerMockTest extends MockIntegrationTest {
	public static final String URL_USERS_USER_TEMPLATES = REST_ENDPOINT_API_PREFIX + "/templates";
	public static final String URL_USERS_USER_TEMPLATE_TEMPLATED = REST_ENDPOINT_API_PREFIX + "/templates/{id}";
	public static final String URL_USERS_USER_TEMPLATES_5555 = URL_USERS_USER_TEMPLATES + "/5555";
	public static final String EMBEDDED = "_embedded";
	public static final String ID_MATCHER = ".id";
	public static final String NAME_MATCHER = ".name";
	public static final String USER_NAME = "user";
	public static final String USER_PASSWORD = "password";
	public static final String LINKS_MATCHER = "_links";

	@Autowired
	@Qualifier(Constants.TEST_USERS_INIT_BEAN_NAME)
	private CommandLineRunner usersInitRunner;

	@Autowired
	private ReportFacade reportFacade;

	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		usersInitRunner.run((String) null);
	}

	@Test
	public void createTemplate() throws Exception {
		ReportTemplateDTO newTemplate = getTemplateDTOWithoutId();

		mvc.perform(MockMvcRequestBuilders.post(URL_USERS_USER_TEMPLATES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(toJson(newTemplate)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$." + EMBEDDED + ID_MATCHER).exists())
				.andExpect(jsonPath("$." + LINKS_MATCHER).exists())
				.andExpect(jsonPath("$." + LINKS_MATCHER).isMap())
				.andExpect(jsonPath("$." + LINKS_MATCHER).isNotEmpty())
				.andExpect(jsonPath("$." + LINKS_MATCHER + ".self").exists())
				.andExpect(jsonPath("$." + LINKS_MATCHER + "." + RelNamesConstants.REPORTS_OF_TEMPLATE).exists())
				.andExpect(jsonPath("$." + LINKS_MATCHER + "." + RelNamesConstants.USER_REPORTS).exists())
				.andExpect(jsonPath("$." + LINKS_MATCHER + "." + RelNamesConstants.USER_REPORT_TEMPLATES).exists())
		;
	}

	@Test
	public void templateCreationWithoutRequiredFields() throws Exception {
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfMonth(null));
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfWeek(null));
		testInvalidTemplate(getTemplateDTOWithoutId().repeatType(null));
		testInvalidTemplate(getTemplateDTOWithoutId().name(null));

		mvc.perform(MockMvcRequestBuilders.post(URL_USERS_USER_TEMPLATES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(toJson(getTemplateDTOWithoutId().description(null))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$." + EMBEDDED + ID_MATCHER).exists());
	}

	private void testInvalidTemplate(ReportTemplateDTO template) throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL_USERS_USER_TEMPLATES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(toJson(template)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void templateCreationRepeatDayOfMonthConstrains() throws Exception {
		testInvalidDayOfMonth(0);
		testInvalidDayOfMonth(-5); // move all different cases to service test (or repository) and only test for response (bad request)
		testInvalidDayOfMonth(31);
		testInvalidDayOfMonth(5000);
		testInvalidDayOfMonth(29);
		testValidRepeatDayOfMonth(28);
		testValidRepeatDayOfMonth(5);
	}

	private void testInvalidDayOfMonth(int repeatDayOfMonth) throws Exception {
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfMonth(repeatDayOfMonth));
	}

	private void testValidRepeatDayOfMonth(int repeatDayOfMonth) throws Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL_USERS_USER_TEMPLATES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.content(toJson(getTemplateDTOWithoutId().repeatDayOfMonth(repeatDayOfMonth))))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$." + EMBEDDED + ID_MATCHER).exists());
	}

	@Test
	public void getTemplates() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL_USERS_USER_TEMPLATES)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
	}

	@Test
	public void getTemplate() throws Exception {
		ReportTemplate createdTemplate = reportFacade.save(getTemplateWithIdForUser(null, USER_NAME).name("RNAME !"));

		mvc.perform(MockMvcRequestBuilders.get(URL_USERS_USER_TEMPLATE_TEMPLATED, createdTemplate.getId())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$." + EMBEDDED + ID_MATCHER).value(createdTemplate.getId()))
				.andExpect(jsonPath("$." + EMBEDDED + NAME_MATCHER).value("RNAME !"));
	}

	@Test
	public void getNotExistingTemplate() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get(URL_USERS_USER_TEMPLATES_5555)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound());
	}

}
