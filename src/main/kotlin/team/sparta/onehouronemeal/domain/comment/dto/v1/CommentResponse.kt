package team.sparta.onehouronemeal.domain.comment.dto.v1

import team.sparta.onehouronemeal.domain.comment.model.v1.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val courseId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id!!,
                courseId = comment.course.id!!,
                userId = comment.user.id!!,
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt
            )
        }
    }
}