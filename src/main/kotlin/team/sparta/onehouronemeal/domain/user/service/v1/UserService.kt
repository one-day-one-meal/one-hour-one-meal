package team.sparta.onehouronemeal.domain.user.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.SignUpRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UpdateUserRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.repository.v1.UserJpaRepository
import team.sparta.onehouronemeal.infra.security.UserPrincipal
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    @Transactional
    fun signUp(role: String, request: SignUpRequest): UserResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    fun signOut() {
        TODO("Not yet implemented")
    }

    fun getUserProfile(userId: Long): UserResponse {
        TODO("Not yet implemented")
    }

    @Transactional
    fun updateUserProfile(userId: Long, request: UpdateUserRequest): UserResponse {
        TODO("Not yet implemented")
    }

    fun tokenTestGenerate(): SignInResponse {
        val testUser = userRepository.findByIdOrNull(1) ?: userRepository.save(
            User(
                username = "test",
                password = "12345678",
                profile = Profile(nickname = "testNickname"),
            )
        )

        return SignInResponse.from(jwtPlugin = jwtPlugin, user = testUser)
    }

    fun tokenTestCheck(accessToken: String, principal: UserPrincipal): TokenCheckResponse {
        val userId = principal.id
        val role = principal.authorities.firstOrNull()?.authority ?: "ROLE_ANONYMOUS"

        return TokenCheckResponse.from(userId, role)
    }
}
