package team.sparta.onehouronemeal.domain.user.repository.v1.subscription

import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription

interface SubscriptionRepository {
    fun findAllByUserId(userId: Long): List<Subscription>

    fun subscribe(subscription: Subscription): Subscription
    fun unsubscribe(userId: Long, subscribedUserId: Long)
    fun isSubscribed(userId: Long, subscribedUserId: Long): Boolean
}