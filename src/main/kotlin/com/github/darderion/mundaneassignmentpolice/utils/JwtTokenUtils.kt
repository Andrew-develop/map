package com.github.darderion.mundaneassignmentpolice.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date
import java.util.stream.Collectors
import javax.crypto.SecretKey

@Component
class JwtTokenUtils (
    @Value("\${jwt.lifetime}")
    private val jwtLifetime: Long,

    private val key: SecretKey = Jwts.SIG.HS256.key().build()
) {

    fun generateToken(userDetails: UserDetails): String {
        val claims = HashMap<String, Any>()
        val rolesList = userDetails.authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        claims["roles"] = rolesList

        val issuedDate = Date()
        val expiredDate = Date(issuedDate.time + jwtLifetime)

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.username)
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(key)
                .compact()
    }

    fun getUsername(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    fun getRoles(token: String): List<String> {
        return getAllClaimsFromToken(token)["roles"] as? List<String> ?: emptyList()
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        val jws = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token) ?: throw Exception(
                String.format("Токен протух или что-то в этом роде...")
        )
        return jws.body
    }
}