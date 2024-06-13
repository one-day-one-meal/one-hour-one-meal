package team.sparta.onehouronemeal.domain.admin.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.comment.dto.v1.report.ReportResponse
import team.sparta.onehouronemeal.domain.comment.repository.v1.report.ReportRepository
import team.sparta.onehouronemeal.domain.course.dto.v1.PendingCourseResponse
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException

@Service
class AdminService(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
    private val reportRepository: ReportRepository,
) {
    fun getPendingUserList(): List<UserResponse> {
        return userRepository.findByStatusOrderByCreatedAtDesc(UserStatus.PENDING).map { UserResponse.from(it) }
    }

    @Transactional
    fun confirmSignUp(userId: Long) {
        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("user", userId)
        if (user.status != UserStatus.PENDING) throw IllegalStateException("해당 사용자는 승인 대기 중 상태가 아닙니다.")
        user.changeStatus(UserStatus.ACTIVE)
    }

    @Transactional
    fun rejectSignUp(userId: Long) {
        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("user", userId)
        if (user.status != UserStatus.PENDING) throw IllegalStateException("해당 사용자는 승인 대기 중 상태가 아닙니다.")
        user.changeStatus(UserStatus.DENIED)
    }

    fun getPendingCourseList(): List<PendingCourseResponse>? {
        return courseRepository.findAllByStatusIsOrderByCreatedAtDesc(CourseStatus.PENDING)
            .map { PendingCourseResponse.from(it) }
    }

    @Transactional
    fun confirmCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("course", courseId)
        if (course.status != CourseStatus.PENDING) throw IllegalStateException("해당 강의는 승인 대기 중 상태가 아닙니다.")
        course.changeStatus(CourseStatus.OPEN)
    }

    @Transactional
    fun rejectCourse(courseId: Long) {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("course", courseId)
        if (course.status != CourseStatus.PENDING) throw IllegalStateException("해당 강의는 승인 대기 중 상태가 아닙니다.")
        course.changeStatus(CourseStatus.DENIED)
    }

    fun getReportList(): List<ReportResponse> {
        return reportRepository.findAllByOrderByCreatedAtDesc().map { ReportResponse.from(it) }
    }

    fun rejectReport(reportId: Long) {
        reportRepository.findByIdOrNull(reportId)
            ?.let { reportRepository.delete(it) }
            ?: throw ModelNotFoundException("report", reportId)
    }
}
