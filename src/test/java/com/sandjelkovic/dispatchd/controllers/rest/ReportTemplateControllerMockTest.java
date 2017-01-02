package com.sandjelkovic.dispatchd.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
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

import static com.sandjelkovic.dispatchd.configuration.Constants.REST_ENDPOINT_API_PREFIX;
import static com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateControllerMockTest.USER_NAME;
import static com.sandjelkovic.dispatchd.controllers.rest.ReportTemplateControllerMockTest.USER_PASSWORD;
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
	public static final String EMBEDDED = "_embedded";
	public static final String ID_MATCHER = ".id";
	public static final String USER_NAME = "user";
	public static final String USER_PASSWORD = "password";

	@Autowired
	@Qualifier(Constants.TEST_USERS_INIT_BEAN_NAME)
	private CommandLineRunner usersInitRunner;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

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
				.content(objectMapper.writeValueAsString(newTemplate)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$." + EMBEDDED + ID_MATCHER).exists());
	}
}
