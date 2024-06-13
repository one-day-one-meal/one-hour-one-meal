package team.sparta.onehouronemeal.domain.user.repository.v1.subscription

import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.Subscription
import team.sparta.onehouronemeal.domain.user.model.v1.subscription.SubscriptionId

@Repository
class SubscriptionRepositoryImpl(
    private val subscriptionJpaRepository: SubscriptionJpaRepository
) : SubscriptionRepository {
    override fun subscribe(subscription: Subscription): Subscription {
        return subscriptionJpaRepository.save(subscription)
    }

    override fun unsubscribe(userId: Long, subscribedUserId: Long) {
        subscriptionJpaRepository.deleteById(SubscriptionId(userId, subscribedUserId))
    }

    override fun isSubscribed(userId: Long, subscribedUserId: Long): Boolean {
        return subscriptionJpaRepository.existsById(SubscriptionId(userId, subscribedUserId))
    }

    override fun findAllByUserId(userId: Long): List<Subscription> {
        return subscriptionJpaRepository.findAllByUserId(userId)
    }
}