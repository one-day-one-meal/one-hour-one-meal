package team.sparta.onehouronemeal.domain.user.repository.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.user.model.v1.User

@Repository
class UserRepositoryImpl(
    val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findById(id: Long): User? {
        return userJpaRepository.findByIdOrNull(id)
    }
}