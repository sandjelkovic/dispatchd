package com.sandjelkovic.dispatchd.auth.service

import com.sandjelkovic.dispatchd.auth.data.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(
        val userRepository: UserRepository
) : UserDetailsService {
    private val accountNotExpired = true
    private val credentialsNotExpired = true
    private val accountNotLocked = true

    override fun loadUserByUsername(username: String?): UserDetails = Optional.ofNullable(username)
            .flatMap(userRepository::findByUsername)
            .map {
                User(it.username, it.password, it.enabled,
                        accountNotExpired, credentialsNotExpired, accountNotLocked,
                        it.authorities)
            }
            .orElseThrow { UsernameNotFoundException("Username $username not found") }
}
