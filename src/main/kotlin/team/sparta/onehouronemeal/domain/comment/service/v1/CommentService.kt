package team.sparta.onehouronemeal.domain.comment.service.v1

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.comment.dto.v1.CreateCommentRequest
import team.sparta.onehouronemeal.domain.comment.repository.v1.CommentRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
)

fun getComment(commentId: Long): CommentResponse {
    TODO()
}

fun createComment(commentId: Long, request: CreateCommentRequest): CommentResponse {
    TODO()
}

fun updateComment(commentId: Long, request: CreateCommentRequest): CommentResponse {
    TODO()
}

fun deleteComment(principal: UserPrincipal, commentId: Long) {
    // User 본인 확인?

    // 본인 댓글만 삭제 가능

    TODO()
}

fun reportComment(principal: UserPrincipal, commentId: Long): Boolean {
    // User 본인 확인
    // 코멘트 아이디를 가지고 코멘트 신고
    // 반환타입 불리언?

    // 본인 댓글에 신고 안됨

    TODO()
}