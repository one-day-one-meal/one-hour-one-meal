package team.sparta.onehouronemeal.domain.user.dto.v1

import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserRole
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus

data class SignUpRequest(
    val username: String,
    val password: String,
    val nickname: String,
) {
    fun to(encoder: PasswordEncoder, role: String): User {
        val actualRole = UserRole.valueOf(role)

        if (actualRole == UserRole.ADMIN) throw IllegalArgumentException("Admin cannot be created by sign up")

        val status = if (actualRole == UserRole.CHEF) UserStatus.PENDING else UserStatus.ACTIVE

        return User(
            username = username,
            password = encoder.encode(password),
            role = actualRole,
            status = status,
            profile = Profile(nickname = nickname),
        )
    }
}
