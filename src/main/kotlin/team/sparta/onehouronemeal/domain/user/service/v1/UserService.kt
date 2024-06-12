package team.sparta.onehouronemeal.domain.user.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponseDto
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckDto
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.repository.v1.UserJpaRepository
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin
import team.sparta.onehouronemeal.infra.security.jwt.SecurityUtils

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val jwtPlugin: JwtPlugin
) {
    fun tokenTestGenerate(): SignInResponseDto {
        val testUser = userRepository.findByIdOrNull(1) ?: userRepository.save(
            User(
                username = "test",
                password = "12345678",
                nickname = "testNickname",
            )
        )

        return SignInResponseDto.from(jwtPlugin = jwtPlugin, user = testUser)
    }

    fun tokenTestCheck(accessToken: String): TokenCheckDto {
        val userId = SecurityUtils.getCurrentUserIdOrNull() ?: throw IllegalStateException("Unauthorized")
        val role = SecurityUtils.getCurrentUserRole()

        return TokenCheckDto.from(userId, role)
    }
}
