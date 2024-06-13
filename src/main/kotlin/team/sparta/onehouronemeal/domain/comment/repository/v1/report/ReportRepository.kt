package team.sparta.onehouronemeal.domain.comment.repository.v1.report

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.comment.model.v1.report.Report

interface ReportRepository : JpaRepository<Report, Long> {
    fun existsByUserIdAndCommentId(userId: Long, commentId: Long): Boolean
}