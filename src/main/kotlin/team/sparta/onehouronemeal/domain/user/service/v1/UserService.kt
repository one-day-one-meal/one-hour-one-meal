package team.sparta.onehouronemeal.domain.user.service.v1

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.SignUpRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.SubscriptionResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.TokenCheckResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UpdateUserRequest
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.UserRole
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.SubscriptionId
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.subscription.SubscriptionRepository
import team.sparta.onehouronemeal.exception.AccessDeniedException
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin

@Service
class UserService(
    private val userRepository: UserRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    @Transactional
    fun signUp(role: String, request: SignUpRequest): UserResponse {
        check(userRepository.existsByUsername(request.username)) { "Username already in use" }
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
        return userRepository.findById(userId)
            ?.also { checkPermission(it, principal) }
            ?.let {
                val subscribedChefList = subscriptionRepository.findAllByUserId(userId)
                UserResponse.from(it, subscribedChefList)
            }
            ?: throw ModelNotFoundException("User not found with id", userId)
    }

    @Transactional
    fun updateUserProfile(userId: Long, principal: UserPrincipal, request: UpdateUserRequest): UserResponse {
        return userRepository.findById(userId)
            ?.also { checkPermission(it, principal) }
            ?.also { request.apply(it) }
            ?.let { UserResponse.from(it) }
            ?: throw ModelNotFoundException("User not found with id", userId)
    }

    @Transactional
    fun tokenTestGenerate(): SignInResponse {
        return (userRepository.findById(1)
            ?: userRepository.save(
                User(
                    username = "test",
                    password = "12345678",
                    profile = Profile(nickname = "testAdminNickname"),
                    role = UserRole.ADMIN
                )
            ))
            .let { SignInResponse.from(jwtPlugin = jwtPlugin, user = it) }
    }

    fun tokenTestCheck(accessToken: String, principal: UserPrincipal): TokenCheckResponse {
        val userId = principal.id
        val role = principal.role

        return TokenCheckResponse.from(userId, role)
    }

    fun subscribeChef(principal: UserPrincipal, chefId: Long): SubscriptionResponse {
        check(!subscriptionRepository.isSubscribed(principal.id, chefId)) { "Already subscribed" }

        val chef = userRepository.findById(chefId)
            ?: throw ModelNotFoundException("Chef not found with id", chefId)

        check(chef.role == UserRole.CHEF) { "User is not a chef" }
        check(chef.status != UserStatus.ACTIVE) { "Chef is not active" }

        val user = userRepository.findById(principal.id)
            ?: throw ModelNotFoundException("User not found with id", principal.id)

        val subscription = Subscription(
            id = SubscriptionId(
                userId = principal.id,
                subscribedUserId = chefId
            ),
            user = user,
            subscribedUser = chef
        )

        return subscriptionRepository.subscribe(subscription)
            .let { SubscriptionResponse.from(it) }
    }

    @Transactional
    fun unsubscribeChef(principal: UserPrincipal, chefId: Long) {
        if (!subscriptionRepository.isSubscribed(principal.id, chefId)) {
            throw IllegalArgumentException("You are not subscribed to this chef")
        }

        subscriptionRepository.unsubscribe(principal.id, chefId)
    }

    private fun checkPermission(user: User, principal: UserPrincipal) {
        check(
            user.checkPermission(
                principal.id,
                principal.role
            )
        ) { throw AccessDeniedException("You do not own this user") }
    }
}
