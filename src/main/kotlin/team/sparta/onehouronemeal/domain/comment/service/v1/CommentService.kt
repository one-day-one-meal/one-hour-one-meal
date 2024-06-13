package team.sparta.onehouronemeal.domain.comment.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.comment.dto.v1.CreateCommentRequest
import team.sparta.onehouronemeal.domain.comment.dto.v1.UpdateCommentRequest
import team.sparta.onehouronemeal.domain.comment.model.v1.Comment
import team.sparta.onehouronemeal.domain.comment.repository.v1.CommentRepository
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.AccessDeniedException
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
) {
    fun getComment(commentId: Long): CommentResponse {
        val comment =
            commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("commentId", commentId)
        return CommentResponse.from(comment)
    }

    @Transactional
    fun createComment(principal: UserPrincipal, courseId: Long, request: CreateCommentRequest): CommentResponse {
        val user = userRepository.findById(principal.id) ?: throw ModelNotFoundException("User", principal.id)
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        val comment = Comment(
            course = course,
            user = user,
            content = request.content,
        )
        return CommentResponse.from(commentRepository.save(comment))
    }

    @Transactional
    fun updateComment(principal: UserPrincipal, commentId: Long, request: UpdateCommentRequest): CommentResponse {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != principal.id) throw AccessDeniedException("You do not own this comment")

        comment.updateComment(request.content)

        return CommentResponse.from(comment)
    }

    @Transactional
    fun deleteComment(principal: UserPrincipal, commentId: Long) {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val comment = commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("Comment", commentId)

        if (comment.user.id != principal.id) throw AccessDeniedException("You do not own this comment")

        commentRepository.delete(comment)
    }

    @Transactional
    fun reportComment(principal: UserPrincipal, commentId: Long) {
        // User 본인 확인
        // 코멘트 아이디를 가지고 코멘트 신고

        // 본인 댓글에 신고 안됨

        TODO()
    }
}