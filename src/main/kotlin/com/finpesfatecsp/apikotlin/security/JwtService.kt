package com.finpesfatecsp.apikotlin.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtService(
    @Value("\${jwt.secret}")
    private val secret: String,
    @Value("\${jwt.expiration:3600000}")
    private val expirationMs: Long,
) {
    private val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun generateToken(
        userId: String,
        userEmail: String,
    ): String =
        Jwts
            .builder()
            .subject(userId)
            .claim("email", userEmail)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + expirationMs))
            .signWith(key)
            .compact()

    fun getSubject(token: String): String = getClaims(token).subject

    fun isValid(token: String): Boolean =
        try {
            !getClaims(token).expiration.before(Date())
        } catch (_: Exception) {
            false
        }

    private fun getClaims(token: String): Claims =
        Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}
