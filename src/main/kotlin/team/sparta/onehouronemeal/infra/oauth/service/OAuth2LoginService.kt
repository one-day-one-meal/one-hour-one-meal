package team.sparta.onehouronemeal.infra.oauth.service

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.user.dto.v1.SignInResponse
import team.sparta.onehouronemeal.domain.user.service.v1.UserService
import team.sparta.onehouronemeal.infra.oauth.client.OAuth2ClientService
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

@Service
class OAuth2LoginService(
    private val oauth2Client: OAuth2ClientService,
    private val userService: UserService
) {
    fun login(provider: OAuth2Provider, authorizationCode: String): SignInResponse {
        return oauth2Client.login(provider, authorizationCode)
            .let { userService.registerIfAbsentWithOAuth(it) }
    }
}
