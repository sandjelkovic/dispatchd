package com.sandjelkovic.dispatchd.report.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * @author sandjelkovic
 * @date 2019-04-06
 */
@Configuration
class SecurityPermitAllConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().permitAll()
            .and().csrf().disable()
    }
}
