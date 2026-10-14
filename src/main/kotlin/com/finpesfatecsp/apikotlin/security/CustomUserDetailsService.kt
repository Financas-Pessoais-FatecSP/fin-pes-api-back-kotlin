package com.finpesfatecsp.apikotlin.security

import com.finpesfatecsp.apikotlin.database.repository.spec.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository
            .findByEmail(username)
            ?.let { User(it.id.toString(), it.password, listOf(SimpleGrantedAuthority("ROLE_USER"))) }
            ?: userRepository
                .findById(
                    username.toLong(),
                )?.let { User(it.id.toString(), it.password, listOf(SimpleGrantedAuthority("ROLE_USER"))) }
            ?: throw Exception("User not found")
}
