package team.sparta.onehouronemeal.domain.user.repository.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus

@Repository
class UserRepositoryImpl(
    val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(id: Long): User? {
        return userJpaRepository.findByIdOrNull(id)
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository.findByUsername(username)
    }

    override fun save(user: User): User {
        return userJpaRepository.save(user)
    }

    override fun delete(user: User) {
        userJpaRepository.delete(user)
    }
    override fun existsById(id: Long): Boolean {
        return userJpaRepository.existsById(id)
    }

    override fun findByStatusOrderByCreatedAtDesc(status: UserStatus): List<User> {
        return userJpaRepository.findByStatusOrderByCreatedAtDesc(status)
    }
}