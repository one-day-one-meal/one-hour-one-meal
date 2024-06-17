package team.sparta.onehouronemeal.domain.user.service.v1.refreshtoken

import io.jsonwebtoken.Claims
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.user.dto.v1.RefreshResponse
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.refreshtoken.RefreshToken
import team.sparta.onehouronemeal.domain.user.repository.v1.refreshtoken.RefreshTokenRepository
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtPlugin: JwtPlugin
) {
    fun validateRefreshToken(token: String): Boolean {
        return jwtPlugin.validateToken(token).isSuccess
    }

    @Transactional
    fun refreshAccessToken(refreshToken: String): RefreshResponse {
        return getValidatedPayload(refreshToken)
            .run {
                val userId = subject.toLong()
                val role = get("role", String::class.java)
                userId to role
            }
            .also { (userId, _) -> checkRefreshToken(userId, refreshToken) }
            .let { (userId, role) -> generateNewToken(userId, role) }
            .let { RefreshResponse(accessToken = it) }
    }

    fun updateRefreshToken(token: String, user: User) {
        refreshTokenRepository.updateToken(
            RefreshToken(
                userId = user.id!!,
                refreshToken = token,
                user = user
            )
        )
    }

    fun deleteRefreshToken(userId: Long) {
        refreshTokenRepository.deleteTokenByUserId(userId)
    }

    private fun getValidatedPayload(refreshToken: String): Claims {
        val jwtValidationResult = jwtPlugin.validateToken(refreshToken)
        if (jwtValidationResult.isFailure) {
            throw IllegalArgumentException("Invalid refresh token")
        }

        return jwtValidationResult.getOrNull()?.payload
            ?: throw IllegalArgumentException("Invalid refresh token")
    }

    private fun checkRefreshToken(userId: Long, refreshToken: String) {
        if (refreshTokenRepository.findTokenByUserId(userId) != refreshToken) {
            throw IllegalArgumentException("Refresh token unmatched")
        }
    }

    private fun generateNewToken(userId: Long, role: String): String {
        val accessToken = jwtPlugin.generateAccessToken(userId.toString(), role)

        return accessToken
    }
}

