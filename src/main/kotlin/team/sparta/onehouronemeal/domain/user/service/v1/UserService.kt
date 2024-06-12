package team.sparta.onehouronemeal.domain.user.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.auth.common.PermissionChecker
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.SignUpRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UpdateUserRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.repository.v1.UserJpaRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin
import team.sparta.onehouronemeal.infra.security.jwt.SecurityUtils

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    @Transactional
    fun signUp(role: String, request: SignUpRequest): UserResponse {
        return request.to(passwordEncoder, role)
            .let { userRepository.save(it) }
            .let { UserResponse.from(it) }
    }

    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        return userRepository.findByUsername(request.username)
            ?.also { check(passwordEncoder.matches(request.password, it.password)) { "Password not matched" } }
            ?.let { SignInResponse.from(jwtPlugin, it) }
            ?: throw IllegalArgumentException("User not found with username")
    }

    @Transactional
    fun signOut() {
        TODO("추후 Refresh token 관련 로직과 함께 구현")
    }

    fun getUserProfile(userId: Long, principal: UserPrincipal): UserResponse {
        return userRepository.findByIdOrNull(userId)
            ?.also { PermissionChecker.check(it.id!!, principal) }
            ?.let { UserResponse.from(it) }
            ?: throw ModelNotFoundException("User not found with id", userId)
    }

    @Transactional
    fun updateUserProfile(userId: Long, principal: UserPrincipal, request: UpdateUserRequest): UserResponse {
        return userRepository.findByIdOrNull(userId)
            ?.also { PermissionChecker.check(it.id!!, principal) }
            ?.also { request.apply(it) }
            ?.let { UserResponse.from(it) }
            ?: throw ModelNotFoundException("User not found with id", userId)
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

    fun tokenTestCheck(accessToken: String): TokenCheckResponse {
        val userId = SecurityUtils.getCurrentUserIdOrNull() ?: throw IllegalStateException("Unauthorized")
        val role = SecurityUtils.getCurrentUserRole()

        return TokenCheckResponse.from(userId, role)
    }
}
