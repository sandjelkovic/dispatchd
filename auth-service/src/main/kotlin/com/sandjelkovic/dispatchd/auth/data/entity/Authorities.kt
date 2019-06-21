package com.sandjelkovic.dispatchd.auth.data.entity

import org.springframework.security.core.GrantedAuthority

enum class Authorities : GrantedAuthority {
    ROLE_USER {
        override fun getAuthority(): String = name
    };
}
