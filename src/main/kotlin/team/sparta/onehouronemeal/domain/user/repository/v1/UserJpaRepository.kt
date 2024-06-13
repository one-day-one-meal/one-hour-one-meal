package team.sparta.onehouronemeal.domain.user.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByStatusOrderByCreatedAtDesc(status: UserStatus): List<User>
    fun existsByUsername(username: String): Boolean
}
