package team.sparta.onehouronemeal.exception

data class AccessDeniedException(
    private val text: String
): RuntimeException(
    "Access Denied: $text"
)