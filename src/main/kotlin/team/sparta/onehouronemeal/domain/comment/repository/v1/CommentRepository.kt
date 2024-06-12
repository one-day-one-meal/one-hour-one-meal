package team.sparta.onehouronemeal.domain.comment.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.comment.model.v1.Comment

interface CommentRepository: JpaRepository<Comment, Long>