package team.sparta.onehouronemeal.domain.user.repository.v1.refreshtoken

import team.sparta.onehouronemeal.domain.user.model.v1.refreshtoken.RefreshToken

interface RefreshTokenRepository {
    fun findTokenByUserId(userId: Long): String?
    fun updateToken(refreshToken: RefreshToken)
    fun updateTokenByUserId(userId: Long, newToken: String)
    fun deleteTokenByUserId(userId: Long)
}