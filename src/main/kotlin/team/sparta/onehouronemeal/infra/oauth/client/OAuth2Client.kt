package team.sparta.onehouronemeal.infra.oauth.client

import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

interface OAuth2Client {
    fun redirectUrl(): String
    fun getAccessToken(code: String): String
    fun getUserInfo(accessToken: String): OAuth2UserInfo
    fun supports(provider: OAuth2Provider): Boolean
}
