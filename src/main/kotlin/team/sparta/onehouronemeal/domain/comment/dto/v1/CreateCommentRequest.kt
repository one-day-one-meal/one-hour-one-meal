package team.sparta.onehouronemeal.domain.comment.dto.v1

data class CreateCommentRequest(
    val courseId: Long,
    val content: String
)