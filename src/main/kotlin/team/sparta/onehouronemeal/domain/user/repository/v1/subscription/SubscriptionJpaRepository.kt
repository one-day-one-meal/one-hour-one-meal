package team.sparta.onehouronemeal.domain.user.repository.v1.subscription

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.SubscriptionId

interface SubscriptionJpaRepository : JpaRepository<Subscription, SubscriptionId> {
    fun findAllByUserId(userId: Long): List<Subscription>
}