package team.sparta.onehouronemeal.domain.user.repository.v1.refreshtoken

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.user.model.v1.refreshtoken.RefreshToken

interface RefreshTokenJpaRepository : JpaRepository<RefreshToken, Long> {
    fun findByUserId(userId: Long): RefreshToken?
    fun deleteByUserId(userId: Long)
}