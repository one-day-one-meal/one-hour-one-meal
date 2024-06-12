package team.sparta.onehouronemeal.domain.user.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.user.model.v1.User

interface UserJpaRepository : JpaRepository<User, Long>
