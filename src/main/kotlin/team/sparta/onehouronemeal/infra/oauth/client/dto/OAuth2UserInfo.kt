package team.sparta.onehouronemeal.infra.oauth.client.dto

import org.springframework.security.crypto.password.PasswordEncoder
import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider
import kotlin.random.Random

open class OAuth2UserInfo(
    val provider: OAuth2Provider,
    val id: String,
    val nickname: String,
    val profileImageUrl: String,
) {
    fun to(encoder: PasswordEncoder): User {
        return User(
            username = generateUsername(id.hashCode().toLong()),
            password = encoder.encode(id),
            provider = provider.name,
            providerId = id,
            profile = Profile(
                nickname = nickname,
                profileImageUrl = profileImageUrl,
            ),
        ).apply { validate() }
    }

    private fun generateUsername(seed: Long): String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val random = Random(seed)

        return (1..28)
            .map { allowedChars[random.nextInt(allowedChars.length)] }
            .joinToString("")
    }
}
