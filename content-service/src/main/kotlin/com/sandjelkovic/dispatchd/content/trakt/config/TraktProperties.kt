package com.sandjelkovic.dispatchd.content.trakt.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("trakt")
data class TraktProperties(
    val baseServiceUrl: String,
    val appId: String,
    val appSecret: String,
    val apiVersion: String
)
