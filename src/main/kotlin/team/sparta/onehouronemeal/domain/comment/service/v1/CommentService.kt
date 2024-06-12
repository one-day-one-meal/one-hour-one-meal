package team.sparta.onehouronemeal.domain.comment.service.v1

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.comment.dto.v1.CreateCommentRequest
import team.sparta.onehouronemeal.domain.comment.dto.v1.UpdateCommentRequest
import team.sparta.onehouronemeal.domain.comment.repository.v1.CommentRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
) {

    fun getComment(commentId: Long): CommentResponse {
        TODO()
    }

    @Transactional
    fun createComment(commentId: Long, request: CreateCommentRequest): CommentResponse {
        TODO()
    }

    @Transactional
    fun updateComment(commentId: Long, request: UpdateCommentRequest): CommentResponse {
        TODO()
    }

    @Transactional
    fun deleteComment(principal: UserPrincipal, commentId: Long) {
        // User 본인 확인?

        // 본인 댓글만 삭제 가능

        TODO()
    }

    @Transactional
    fun reportComment(principal: UserPrincipal, commentId: Long) {
        // User 본인 확인
        // 코멘트 아이디를 가지고 코멘트 신고

        // 본인 댓글에 신고 안됨

        TODO()
    }
}