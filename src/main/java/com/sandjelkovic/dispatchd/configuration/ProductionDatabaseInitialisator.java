package com.sandjelkovic.dispatchd.configuration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
public class ProductionDatabaseInitialisator {
}
