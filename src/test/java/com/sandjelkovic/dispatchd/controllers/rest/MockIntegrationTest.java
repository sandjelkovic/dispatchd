package com.sandjelkovic.dispatchd.controllers.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandjelkovic.dispatchd.configuration.Constants;
import com.sandjelkovic.dispatchd.data.dto.ReportTemplateDTO;
import com.sandjelkovic.dispatchd.data.entities.ReportTemplate;
import com.sandjelkovic.dispatchd.data.entities.User;
import com.sandjelkovic.dispatchd.data.repositories.UserRepository;
import com.sandjelkovic.dispatchd.exception.UserNotFoundException;
import com.sandjelkovic.dispatchd.helpers.TestDataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author ${sandjelkovic}
 * @date 2.1.17.
 */
public class MockIntegrationTest {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private List<User> users;

	@Autowired
	@Qualifier(Constants.TEST_USERS_INIT_BEAN_NAME)
	private CommandLineRunner usersInitRunner;

	@Autowired
	private TestDataGenerator dataGenerator;

	@Autowired
	private ObjectMapper objectMapper;

	protected ReportTemplate getTemplateWithIdForUser(Long id, String username) {
		return dataGenerator.getTemplateWithIdForUser(id, userRepository.findOneByUsername(username)
				.orElseThrow(UserNotFoundException::new));
	}

	protected ReportTemplateDTO getTemplateDTOWithoutId() {
		return dataGenerator.getTemplateDTOWithoutId();
	}

	protected String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
}
