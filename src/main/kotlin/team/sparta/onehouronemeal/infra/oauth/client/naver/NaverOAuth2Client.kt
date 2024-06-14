package team.sparta.onehouronemeal.infra.oauth.client.naver

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import team.sparta.onehouronemeal.infra.oauth.client.OAuth2Client
import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.client.naver.dto.NaverTokenResponse
import team.sparta.onehouronemeal.infra.oauth.client.naver.dto.NaverUserInfoResponse
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

@Component
class NaverOAuth2Client(
    @Value("\${naver.client.id}") val clientId: String,
    @Value("\${naver.client.secret}") val secret: String,
    @Value("\${naver.redirect.url}") val redirectUrl: String,
    @Value("\${naver.api.auth_url}") val authUrl: String,
    @Value("\${naver.api.token_url}") val tokenUrl: String,
    @Value("\${naver.api.profile_url}") val userUrl: String,
    private val restClient: RestClient
) : OAuth2Client {
    override fun redirectUrl(): String {
        return authUrl +
            "?client_id=${clientId}" +
            "&redirect_uri=${redirectUrl}" +
            "&state=test" +
            "&response_type=code"
    }

    override fun getAccessToken(code: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "client_secret" to secret,
            "code" to code,
            "state" to "test"
        )

        return restClient.post()
            .uri(tokenUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("네이버 AccessToken 조회 실패")
            }
            .body<NaverTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("네이버 AccessToken 조회 실패")
    }

    override fun getUserInfo(accessToken: String): OAuth2UserInfo {
        return restClient.get()
            .uri(userUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("네이버 UserInfo 조회 실패")
            }
            .body<NaverUserInfoResponse>()
            ?: throw RuntimeException("네이버 UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.NAVER
    }
}