package team.sparta.onehouronemeal.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshTokenExpirationHour}") private val refreshTokenExpirationHour: Long,
) {
    companion object {
        const val ACCESS_TOKEN_TYPE = "access"
        const val REFRESH_TOKEN_TYPE = "refresh"
    }

    fun validateToken(jwt: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt)
        }
    }

    fun generateAccessToken(subject: String, role: String): String {
        return generateToken(subject, role, ACCESS_TOKEN_TYPE, Duration.ofHours(accessTokenExpirationHour))
    }

    fun generateRefreshToken(subject: String, role: String): String {
        return generateToken(subject, role, REFRESH_TOKEN_TYPE, Duration.ofHours(refreshTokenExpirationHour))
    }

    private fun generateToken(subject: String, role: String, type: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims().add(mapOf("role" to role, "type" to type)).build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}