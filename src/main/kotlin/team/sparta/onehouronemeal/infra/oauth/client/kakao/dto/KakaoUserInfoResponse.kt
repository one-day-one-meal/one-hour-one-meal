package team.sparta.onehouronemeal.infra.oauth.client.kakao.dto

import com.fasterxml.jackson.annotation.JsonProperty
import team.sparta.onehouronemeal.infra.oauth.client.dto.OAuth2UserInfo
import team.sparta.onehouronemeal.infra.oauth.common.OAuth2Provider

class KakaoUserInfoResponse(
    id: Long,
    properties: KakaoUserInfoPropertiesResponse
) : OAuth2UserInfo(
    provider = OAuth2Provider.KAKAO,
    id = id.toString(),
    nickname = properties.nickname,
    profileImageUrl = properties.profileImageUrl
)

data class KakaoUserInfoPropertiesResponse(
    val nickname: String,
    @JsonProperty("profile_image")
    val profileImageUrl: String
)

