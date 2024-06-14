package team.sparta.onehouronemeal.domain.admin.controller.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import team.sparta.onehouronemeal.domain.admin.service.v1.AdminService
import team.sparta.onehouronemeal.domain.comment.dto.v1.report.ReportResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.PendingCourseResponse
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
class AdminController(
    private val adminService: AdminService
) {

    @GetMapping("/sign-up/pending")
    fun getPendingUserList(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(adminService.getPendingUserList())
    }

    @PatchMapping("/sign-up/{userId}/confirm")
    fun confirmUser(@PathVariable userId: Long): ResponseEntity<Unit> {
        adminService.confirmSignUp(userId)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/sign-up/{userId}/reject")
    fun rejectUser(@PathVariable userId: Long): ResponseEntity<Unit> {
        adminService.rejectSignUp(userId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/courses/pending")
    fun getPendingCourseList(): ResponseEntity<List<PendingCourseResponse>> {
        return ResponseEntity.ok(adminService.getPendingCourseList())
    }

    @PatchMapping("/courses/{courseId}/confirm")
    fun confirmCourse(@PathVariable courseId: Long): ResponseEntity<Unit> {
        adminService.confirmCourse(courseId)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/courses/{courseId}/reject")
    fun rejectCourse(@PathVariable courseId: Long): ResponseEntity<Unit> {
        adminService.rejectCourse(courseId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/reports")
    fun getReportList(): ResponseEntity<List<ReportResponse>> {
        return ResponseEntity.ok(adminService.getReportList())
    }

    @DeleteMapping("/reports/{reportId}")
    fun rejectReport(@PathVariable reportId: Long): ResponseEntity<Unit> {
        adminService.rejectReport(reportId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}