package team.sparta.onehouronemeal.domain.user.dto.v1

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserRole
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus

data class SignUpRequest(
    @field:Pattern(
        regexp = "^[a-z\\d]{4,10}$",
        message = "Username must be between 4 and 10 characters and contain only lowercase letters and numbers."
    )
    val username: String,
    @field:Pattern(
        regexp = "^[a-zA-Z\\d!@#$%^&*]{8,15}$",
        message = "Password must be between 8 and 15 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    val password: String,
    val confirmPassword: String,
    @Size(max = 20, message = "Nickname must be less than 20 characters")
    val nickname: String
) {
    fun to(encoder: PasswordEncoder, role: String, imageUrl: String?): User {
        val actualRole = UserRole.valueOf(role)

        if (actualRole == UserRole.ADMIN) throw IllegalArgumentException("Admin cannot be created by sign up")

        val status = if (actualRole == UserRole.CHEF) UserStatus.PENDING else UserStatus.ACTIVE

        return User(
            username = username,
            password = encoder.encode(password),
            role = actualRole,
            status = status,
            profile = Profile(nickname = nickname, profileImageUrl = imageUrl),
        ).apply { validate() }
    }
}
