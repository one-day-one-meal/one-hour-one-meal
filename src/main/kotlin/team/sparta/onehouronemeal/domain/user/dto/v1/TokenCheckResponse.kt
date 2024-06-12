package team.sparta.onehouronemeal.domain.user.dto.v1

data class TokenCheckResponse(
    val userId: Long,
    val userRole: String,
) {
    companion object {
        fun from(userId: Long, userRole: String): TokenCheckResponse {
            return TokenCheckResponse(userId, userRole)
        }
    }
}
