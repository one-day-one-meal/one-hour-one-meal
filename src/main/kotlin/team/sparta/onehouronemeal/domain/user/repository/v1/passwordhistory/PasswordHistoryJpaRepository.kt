package team.sparta.onehouronemeal.domain.user.repository.v1.passwordhistory

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory.PasswordHistory

interface PasswordHistoryJpaRepository : JpaRepository<PasswordHistory, Long> {
    fun deleteAllByUserId(userId: Long)
}