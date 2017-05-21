package com.sandjelkovic.dispatchd.configuration;

public final class Constants {
	public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
	public static final String SPRING_PROFILE_PRODUCTION = "prod";
	public static final String SPRING_PROFILE_TESTING = "testing";

	public static final String[] ADMIN_ROLES = {"ROLE_USER", "ROLE_ADMIN"};
	public static final String[] DEFAULT_USER_ROLES = {"ROLE_USER"};

	public static final String CONVERSION_SERVICE_BEAN_NAME = "mvcConversionService";
	public static final String TEST_USERS_INIT_BEAN_NAME = "testUsersInit";
	public static final String REST_ENDPOINT_API_PREFIX = "/api";

	public static final int DEFAULT_PAGE_SIZE = 5;

	private Constants() {
	}
}
