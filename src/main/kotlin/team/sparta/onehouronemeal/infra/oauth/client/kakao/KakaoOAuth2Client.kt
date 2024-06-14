package team.sparta.onehouronemeal.infra.oauth.client.kakao

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import team.sparta.onehouronemeal.infra.oauth.client.OAuth2Client
import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.client.kakao.dto.KakaoTokenResponse
import team.sparta.onehouronemeal.infra.oauth.client.kakao.dto.KakaoUserInfoResponse
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

@Component
class KakaoOAuth2Client(
    @Value("\${kakao.client.id}") val clientId: String,
    @Value("\${kakao.redirect.url}") val redirectUrl: String,
    @Value("\${kakao.api.auth_url}") val authUrl: String,
    @Value("\${kakao.api.token_url}") val tokenUrl: String,
    @Value("\${kakao.api.profile_url}") val userUrl: String,
    private val restClient: RestClient
) : OAuth2Client {

    override fun redirectUrl(): String {
        return StringBuilder(authUrl)
            .append("?client_id=").append(clientId)
            .append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=").append("code")
            .toString()
    }

    override fun getAccessToken(code: String): String {
        val requestData = mutableMapOf(
            "grant_type" to "authorization_code",
            "client_id" to clientId,
            "code" to code
        )

        return restClient.post()
            .uri(tokenUrl)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(LinkedMultiValueMap<String, String>().apply { this.setAll(requestData) })
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("카카오 AccessToken 조회 실패")
            }
            .body<KakaoTokenResponse>()
            ?.accessToken
            ?: throw RuntimeException("카카오 AccessToken 조회 실패")
    }

    override fun getUserInfo(accessToken: String): OAuth2UserInfo {
        return restClient.get()
            .uri(userUrl)
            .header("Authorization", "Bearer $accessToken")
            .retrieve()
            .onStatus(HttpStatusCode::isError) { _, _ ->
                throw RuntimeException("카카오 UserInfo 조회 실패")
            }
            .body<KakaoUserInfoResponse>()
            ?: throw RuntimeException("카카오 UserInfo 조회 실패")
    }

    override fun supports(provider: OAuth2Provider): Boolean {
        return provider == OAuth2Provider.KAKAO
    }
}