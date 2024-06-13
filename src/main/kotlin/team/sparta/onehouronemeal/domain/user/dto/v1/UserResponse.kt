package team.sparta.onehouronemeal.domain.user.dto.v1

import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription
import java.time.LocalDateTime

data class UserResponse(
    val userId: Long,
    val nickname: String,
    val role: String,
    val status: String,
    val profileImageUrl: String? = null,
    val provider: String? = null,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val subscribedChefList: List<SubscriptionResponse>?,
) {
    companion object {
        fun from(user: User, subscribedChefList: List<Subscription>? = null): UserResponse {
            return UserResponse(
                userId = user.id!!,
                nickname = user.profile.nickname,
                role = user.role.name,
                status = user.status.name,
                profileImageUrl = user.profile.profileImageUrl,
                provider = user.provider,
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                subscribedChefList = subscribedChefList?.map { SubscriptionResponse.from(it) }
            )
        }
    }
}
