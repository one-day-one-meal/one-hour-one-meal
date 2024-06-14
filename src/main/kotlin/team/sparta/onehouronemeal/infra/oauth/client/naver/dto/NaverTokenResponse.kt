package team.sparta.onehouronemeal.infra.oauth.client.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class NaverTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String,
)
