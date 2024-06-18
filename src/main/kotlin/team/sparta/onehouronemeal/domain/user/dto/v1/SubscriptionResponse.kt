package team.sparta.onehouronemeal.domain.user.dto.v1

import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription

data class SubscriptionResponse(
    val subscribedChefId: Long,
    val nickname: String,
) {
    companion object {
        fun from(subscription: Subscription): SubscriptionResponse {
            return SubscriptionResponse(
                subscribedChefId = subscription.id.subscribedUserId,
                nickname = subscription.subscribedUser.profile.nickname
            )
        }
    }
}