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
    companion object {
        fun to(encoder: PasswordEncoder, role: String, request: SignUpRequest): User {
            val actualRole = UserRole.valueOf(role)
            val status = if (actualRole == UserRole.CHEF) UserStatus.PENDING else UserStatus.ACTIVE

            return User(
                username = request.username,
                password = encoder.encode(request.password),
                role = actualRole,
                status = status,
                profile = Profile(nickname = request.nickname),
            )
        }
    }
}
