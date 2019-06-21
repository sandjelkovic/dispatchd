package com.sandjelkovic.dispatchd.auth.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class UserModel (
        @Id val id : String?,
        @Indexed val username : String,
        val password : String,
        val activated : Boolean = false,
        val enabled : Boolean = true,
        val activationKey: String?,
        val resetPasswordKey : String?,
        val authorities: Set<Authorities> = setOf()
)
