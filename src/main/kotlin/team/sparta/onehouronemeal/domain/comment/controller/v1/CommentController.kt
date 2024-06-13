package team.sparta.onehouronemeal.domain.comment.controller.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.comment.dto.v1.CreateCommentRequest
import team.sparta.onehouronemeal.domain.comment.dto.v1.UpdateCommentRequest
import team.sparta.onehouronemeal.domain.comment.service.v1.CommentService
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@RequestMapping("/api/v1/courses/{courseId}/comments")
@RestController
class CommentController(private val commentService: CommentService) {

    @GetMapping("/{commentId}")
    fun getComment(@PathVariable courseId: Long, @PathVariable commentId: Long): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.getComment(commentId))
    }

    @PostMapping
    fun createComment(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable courseId: Long,
        @RequestBody request: CreateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(commentService.createComment(principal, courseId, request))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable courseId: Long, @PathVariable commentId: Long,
        @RequestBody request: UpdateCommentRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.ok(commentService.updateComment(principal, commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        commentService.deleteComment(principal, commentId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{commentId}/reports")
    fun reportComment(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable commentId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(commentService.reportComment(principal, commentId))
    }
}