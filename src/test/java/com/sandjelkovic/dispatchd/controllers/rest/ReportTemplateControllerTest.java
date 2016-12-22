package com.sandjelkovic.dispatchd.controllers.rest;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.data.entities.ReportRepeatType;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.ReportTemplateRepository;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.service.ReportService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {DispatchdApplication.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
public class ReportTemplateControllerTest {
	public static final String URL_USERS_USER_TEMPLATES = "/users/user/templates";
	public static final String EMBEDDED = "_embedded";
	public static final String ID_MATCHER = ".id";
	public static final String NAME_MATCHER = ".name";
	public static final String REPEAT_TYPE_MATCHER = ".repeatType";
	public static final String REPEAT_DAY_OF_MONTH_MATCHER = ".repeatDayOfMonth";
	public static final String REPEAT_DAY_OF_WEEK_MATCHER = ".repeatDayOfWeek";
	public static final String TIME_OF_DAY_TO_DELIVER_MATCHER = ".timeOfDayToDeliver";
	public static final String USERNAME_MATCHER = "username";
	public static final String URL_USERS_USER_TEMPLATES_1 = URL_USERS_USER_TEMPLATES + "/1";
	public static final String URL_USERS_USER_TEMPLATES_5555 = URL_USERS_USER_TEMPLATES + "/5555";
	public static final String USER_NAME = "user";
	public static final String USER_PASSWORD = "password";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReportService reportService;

	@Autowired
	private ReportTemplateRepository reportTemplateRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private List<User> users;

	@Autowired
	@Qualifier(Constants.TEST_USERS_INIT_BEAN_NAME)
	private CommandLineRunner usersInitRunner;

	@BeforeTransaction
	public void wipeDatabase() {
		reportTemplateRepository.deleteAll();
	}
	@Before
	public void setUp() {
		RestAssured.reset();
		try {
			usersInitRunner.run(null);
		} catch (Exception e) {
		}
	}

	@Test
	public void createTemplate() throws Exception {
		ReportTemplateDTO newTemplate = getTemplateDTOWithoutId();

		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(newTemplate)
				.when()
				.post(URL_USERS_USER_TEMPLATES)
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.body(EMBEDDED + ID_MATCHER, notNullValue())
				.body(EMBEDDED + NAME_MATCHER, notNullValue())
				.body(EMBEDDED + REPEAT_TYPE_MATCHER, notNullValue())
				.body(EMBEDDED + REPEAT_DAY_OF_MONTH_MATCHER, notNullValue())
				.body(EMBEDDED + REPEAT_DAY_OF_WEEK_MATCHER, notNullValue())
				.body(EMBEDDED + TIME_OF_DAY_TO_DELIVER_MATCHER, notNullValue())
				.body(EMBEDDED + USERNAME_MATCHER, nullValue());
	}

	@Test
	public void templateCreationWithoutRequiredFields() throws Exception {
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfMonth(null));
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfWeek(null));
		testInvalidTemplate(getTemplateDTOWithoutId().repeatType(null));
		testInvalidTemplate(getTemplateDTOWithoutId().name(null));
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getTemplateDTOWithoutId().description(null))
				.when()
				.post(URL_USERS_USER_TEMPLATES)
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	private void testInvalidTemplate(ReportTemplateDTO template) {
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(template)
				.when()
				.post(URL_USERS_USER_TEMPLATES)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void templateCreationRepeatDayOfMonthConstrains() throws Exception {
		testInvalidDayOfMonth(0);
		testInvalidDayOfMonth(-5);
		testInvalidDayOfMonth(31);
		testInvalidDayOfMonth(5000);
		testInvalidDayOfMonth(29);
		testValidRepeatDayOfMonth(28);
		testValidRepeatDayOfMonth(5);
	}

	private void testInvalidDayOfMonth(int repeatDayOfMonth) {
		testInvalidTemplate(getTemplateDTOWithoutId().repeatDayOfMonth(repeatDayOfMonth));
	}

	private void testValidRepeatDayOfMonth(int repeatDayOfMonth) {
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(getTemplateDTOWithoutId().repeatDayOfMonth(repeatDayOfMonth))
				.when()
				.post(URL_USERS_USER_TEMPLATES)
				.then()
				.statusCode(HttpStatus.CREATED.value());
	}

	private ReportTemplateDTO getTemplateDTOWithoutId() {
		return new ReportTemplateDTO()
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

	@Test
	public void basicCreateOnIdTest() {
		ReportTemplateDTO newTemplate = new ReportTemplateDTO()
				.id(1L)
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);

		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.and().contentType(ContentType.JSON)
				.and().accept(ContentType.JSON)
				.and().body(newTemplate)
				.when()
				.put(URL_USERS_USER_TEMPLATES_1)
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void getTemplates() {
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.and().contentType(ContentType.JSON)
				.and().accept(ContentType.JSON)
				.when()
				.get(URL_USERS_USER_TEMPLATES)
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void getTemplate() {
		ReportTemplate createdTemplate = reportService.save(createTemplateWithIdForUser(null, USER_NAME).name("RNAME !"));
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.and().contentType(ContentType.JSON)
				.and().accept(ContentType.JSON)
				.when()
				.get(URL_USERS_USER_TEMPLATES + "/" + createdTemplate.getId())
				.then()
				.statusCode(HttpStatus.OK.value());
	}

	@Test
	public void getNotExistingTemplate() {
		given()
				.auth().basic(USER_NAME, USER_PASSWORD)
				.and().contentType(ContentType.JSON)
				.and().accept(ContentType.JSON)
				.when()
				.get(URL_USERS_USER_TEMPLATES_5555)
				.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private ReportTemplate createTemplateWithIdForUser(Long id, String username) {
		return new ReportTemplate()
				.id(id)
				.user(userRepository.findOneByUsername(username).orElseThrow(UserNotFoundException::new))
				.active(true)
				.description("[Description] for the test template")
				.name("Test report template [name]")
				.repeatType(ReportRepeatType.WEEKLY)
				.repeatInterval(ChronoUnit.WEEKS)
				.repeatDayOfMonth(2)
				.repeatDayOfWeek(DayOfWeek.FRIDAY)
				.timeOfDayToDeliver(LocalTime.NOON);
	}

}
