package team.sparta.onehouronemeal.infra.oauth.client

import jakarta.transaction.NotSupportedException
import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

@Service
class OAuth2ClientService(
    private val clients: List<OAuth2Client>
) {
    fun redirectUrlBy(provider: OAuth2Provider): String {
        return selectClient(provider).redirectUrl()
    }

    fun login(provider: OAuth2Provider, authorizationCode: String): OAuth2UserInfo {
        val client = selectClient(provider)
        return client.getAccessToken(authorizationCode)
            .let { client.getUserInfo(it) }
    }

    private fun selectClient(provider: OAuth2Provider): OAuth2Client {
        return clients.find { it.supports(provider) } ?: throw NotSupportedException("지원하지 않는 OAuth Provider 입니다.")
    }
}
