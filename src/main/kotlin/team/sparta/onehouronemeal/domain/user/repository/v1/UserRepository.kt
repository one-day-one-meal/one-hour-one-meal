package team.sparta.onehouronemeal.domain.user.repository.v1

import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus

interface UserRepository {
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
    fun findByStatusOrderByCreatedAtDesc(status: UserStatus): List<User>
    fun findByProviderAndProviderId(provider: String, providerId: String): User?

    fun existsById(id: Long): Boolean
    fun existsByUsername(username: String): Boolean

    fun save(user: User): User
    fun delete(user: User)
}