package team.sparta.onehouronemeal.infra.oauth.client.naver.dto

import com.fasterxml.jackson.annotation.JsonProperty
import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

class NaverUserInfoResponse(
    @JsonProperty("response")
    response: NaverUserInfoDetailResponse
) : OAuth2UserInfo(
    provider = OAuth2Provider.NAVER,
    id = response.id,
    nickname = response.nickname,
    profileImageUrl = response.profileImageUrl
)

data class NaverUserInfoDetailResponse(
    val id: String,
    val nickname: String,
    val email: String,
    val name: String,
    @JsonProperty("profile_image")
    val profileImageUrl: String
)

