package team.sparta.onehouronemeal.domain.user.dto.v1

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User

data class UpdateUserRequest(
    @Size(max = 20, message = "Nickname must be less than 20 characters")
    val nickname: String,
    @field:Pattern(
        regexp = "^$|^[a-zA-Z\\\\d!@#$%^&*]{8,15}$",
        message = "Password must be between 8 and 15 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    val password: String,
    val confirmPassword: String
) {
    fun apply(encoder: PasswordEncoder, user: User, imageUrl: String?) {
        user.updateProfile(Profile(nickname, imageUrl))
        if (password.isNotEmpty() && password == confirmPassword) {
            user.updatePassword(encoder.encode(password))
        }
    }
}