package team.sparta.onehouronemeal.domain.user.repository.v1

import team.sparta.onehouronemeal.domain.user.model.v1.User

interface UserRepository {
    fun findById(id: Long): User?
    fun findByUsername(username: String): User?
    fun save(user: User): User
    fun delete(user: User)
}