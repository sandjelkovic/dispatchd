package com.sandjelkovic.dispatchd.configuration;

import com.sandjelkovic.dispatchd.domain.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserLoginService userLoginService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}

	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userLoginService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/").permitAll();
//		http.authorizeRequests().antMatchers(HttpMethod.GET, "/home").permitAll();
//		http.formLogin().loginPage("/login").permitAll().and().logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/logout-success")
//				.permitAll();
//		// .logoutUrl("/logout").clearAuthentication(true).logoutSuccessUrl("/logout-success")
		//http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
//
//		http.authorizeRequests().antMatchers("/css/**").permitAll();
//		http.authorizeRequests().antMatchers("/js/**").permitAll();
//		http.authorizeRequests().antMatchers("/dist/**").permitAll();
//		http.authorizeRequests().antMatchers("/less/**").permitAll();
//		http.authorizeRequests().antMatchers("/jquery/**").permitAll();
		http.authorizeRequests()
				.antMatchers("/users/{username}/**").access("#username == authentication.name")
				.antMatchers("/actuator**").permitAll()
				.antMatchers("/actuator/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic()
				.and()
				.csrf().disable();

		// @PreAuthorize("hasRole('XYZ')")
		// is the same as
		// @PreAuthorize("hasAuthority('ROLE_XYZ')")

		// http.authorizeRequests()
		// .antMatchers("/users").hasRole("ADMIN")
		// .antMatchers("/review").authenticated()
		// .antMatchers("/logreview").authenticated()
		// .antMatchers("/oauth/token").authenticated();
	}
}
