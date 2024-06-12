package team.sparta.onehouronemeal.domain.user.dto.v1

import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.infra.security.jwt.JwtPlugin

data class SignInResponseDto(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(jwtPlugin: JwtPlugin, user: User): SignInResponseDto {
            val accessToken = jwtPlugin.generateAccessToken(user.id.toString(), user.role.name)
            val refreshToken = jwtPlugin.generateRefreshToken(user.id.toString(), user.role.name)

            return SignInResponseDto(accessToken, refreshToken)
        }
    }
}