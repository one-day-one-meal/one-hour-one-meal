package team.sparta.onehouronemeal.infra.oauth.service

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.user.service.v1.UserService
import team.sparta.onehouronemeal.infra.oauth.client.OAuth2ClientService
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin

@Service
class OAuth2LoginService(
    private val oauth2Client: OAuth2ClientService,
    private val userService: UserService,
    private val jwtPlugin: JwtPlugin
) {
    fun login(provider: OAuth2Provider, authorizationCode: String): String {
        return oauth2Client.login(provider, authorizationCode)
            .let { userService.registerIfAbsentWithOAuth(it) }
            .let {
                jwtPlugin.generateAccessToken(
                    subject = it.userId.toString(),
                    role = it.role
                )
            }
    }
}
