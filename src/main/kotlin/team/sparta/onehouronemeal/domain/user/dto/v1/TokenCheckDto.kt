package team.sparta.onehouronemeal.domain.user.dto.v1

data class TokenCheckDto(
    val userId: Long,
    val userRole: String,
) {
    companion object {
        fun from(userId: Long, userRole: String): TokenCheckDto {
            return TokenCheckDto(userId, userRole)
        }
    }
}
