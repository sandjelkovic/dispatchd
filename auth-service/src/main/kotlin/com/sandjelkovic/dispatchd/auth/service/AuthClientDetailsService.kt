package com.sandjelkovic.dispatchd.auth.service

import com.sandjelkovic.dispatchd.auth.data.repository.AuthClientRepository
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthClientDetailsService(
        val authClientRepository: AuthClientRepository
) : ClientDetailsService {
    override fun loadClientByClientId(clientId: String?): ClientDetails = Optional.ofNullable(clientId)
            .flatMap(authClientRepository::findByClientId)
            .map { BaseClientDetails(it.clientId, it.resources, it.scopes, it.grantTypes, "", it.redirectUris) }
            .orElseThrow { IllegalArgumentException() }

}
