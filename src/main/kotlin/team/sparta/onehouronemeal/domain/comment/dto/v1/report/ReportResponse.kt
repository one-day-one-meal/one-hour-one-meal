package team.sparta.onehouronemeal.domain.comment.dto.v1.report

import team.sparta.onehouronemeal.domain.comment.model.v1.report.Report
import java.time.LocalDateTime

data class ReportResponse(
    val id: Long,
    val commentId: Long,
    val userId: Long,
    val description: String,
    val createdAt: LocalDateTime?,
) {
    companion object {
        fun from(report: Report): ReportResponse {
            return ReportResponse(
                id = report.id!!,
                commentId = report.comment.id!!,
                userId = report.user.id!!,
                description = report.description,
                createdAt = report.createdAt,
            )
        }
    }
}
