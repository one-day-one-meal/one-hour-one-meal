package team.sparta.onehouronemeal.domain.user.repository.v1.refreshtoken

import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.user.model.v1.refreshtoken.RefreshToken

@Repository
class RefreshTokenRepositoryImpl(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : RefreshTokenRepository {
    override fun findTokenByUserId(userId: Long): String? {
        return refreshTokenJpaRepository.findByUserId(userId)?.refreshToken
    }

    override fun updateToken(refreshToken: RefreshToken) {
        refreshTokenJpaRepository.findByUserId(refreshToken.userId)
            ?.updateToken(refreshToken.refreshToken)
            ?: refreshTokenJpaRepository.save(refreshToken)
    }

    override fun deleteTokenByUserId(userId: Long) {
        refreshTokenJpaRepository.deleteByUserId(userId)
    }
}