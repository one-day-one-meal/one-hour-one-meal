package team.sparta.onehouronemeal.domain.user.dto.v1

import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User

data class UpdateUserRequest(
    val nickname: String,
    val password: String,
    val confirmPassword: String
) {
    fun apply(encoder: PasswordEncoder, user: User, imageUrl: String?) {
        user.updateProfile(Profile(nickname, imageUrl))
        user.updatePassword(encoder.encode(password))
    }
}