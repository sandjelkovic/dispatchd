package com.sandjelkovic.dispatchd.auth.data.entity

import org.springframework.data.annotation.Id

data class AuthClientDetails(
        @Id val id : String?,
        val clientId : String?,
        val clientSecret : String?,
        val grantTypes : String?,
        val scopes : String?,
        val resources : String?,
        val redirectUris : String?,
        val accessTokenValidity : Int?,
        val refreshTokenValidity : Int?,
        val additionalInformation : String?
)
