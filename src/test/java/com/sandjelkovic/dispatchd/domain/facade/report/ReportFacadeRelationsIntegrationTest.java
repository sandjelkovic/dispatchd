package com.sandjelkovic.dispatchd.domain.facade.report;

import com.sandjelkovic.dispatchd.DispatchdApplication;
import com.sandjelkovic.dispatchd.domain.facade.ReportFacade;
import com.sandjelkovic.dispatchd.helpers.TestDataGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sandjelkovic
 * @date 1.4.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = {"testing"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {DispatchdApplication.class})

public class ReportFacadeRelationsIntegrationTest {

	@Autowired
	private ReportFacade target;
	@Autowired
	private TestDataGenerator testDataGenerator;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void getReportTemplatesToBeGeneratedBetween() throws Exception {

	}

	@Test
	public void save() throws Exception {

	}

	@Test
	public void save1() throws Exception {

	}

	@Test
	public void saveNoUserContext() throws Exception {

	}

	@Test
	public void findGenerated() throws Exception {

	}

	@Test
	public void findTemplate() throws Exception {

	}

	@Test
	public void delete() throws Exception {

	}

	@Test
	public void delete1() throws Exception {

	}

	@Test
	public void deleteTemplate() throws Exception {

	}

	@Test
	public void deleteGenerated() throws Exception {

	}

	@Test
	public void findAll() throws Exception {

	}

	@Test
	public void findAll1() throws Exception {

	}

	@Test
	public void findTemplatesForUser() throws Exception {

	}

	@Test
	public void findGeneratedForUser() throws Exception {

	}

	@Test
	public void findGeneratedByTemplateForUser() throws Exception {

	}

	@Test
	public void getNewGenerationTimeForTemplate() throws Exception {

	}

	@Test
	public void findTemplateShows() throws Exception {

	}

	@Test
	public void disconnectAllShows() throws Exception {

	}

	@Test
	public void disconnectShow() throws Exception {

	}

	@Test
	public void connectShow() throws Exception {
	}

}
