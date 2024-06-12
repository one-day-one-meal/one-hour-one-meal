package team.sparta.onehouronemeal.domain.user.dto.v1

import team.sparta.onehouronemeal.domain.user.model.v1.Profile
import team.sparta.onehouronemeal.domain.user.model.v1.User

data class UpdateUserRequest(
    val nickname: String
) {
    fun apply(user: User) {
        user.updateProfile(Profile(nickname))
    }
}