package com.sandjelkovic.dispatchd.auth.config

import com.sandjelkovic.dispatchd.auth.MongoTokenStore
import com.sandjelkovic.dispatchd.auth.service.AuthClientDetailsService
import com.sandjelkovic.dispatchd.auth.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore


@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationConfig(
        @Qualifier("authenticationManagerBean") val authenticationManager: AuthenticationManager,
        val userDetailsService: CustomUserDetailsService,
        val authClientDetailsService: AuthClientDetailsService,
        val encoder: PasswordEncoder
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(authClientDetailsService)
    }

    @Bean
    fun tokenStore(): TokenStore {
        return MongoTokenStore()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }

    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(encoder)
                .allowFormAuthenticationForClients()
    }

}
