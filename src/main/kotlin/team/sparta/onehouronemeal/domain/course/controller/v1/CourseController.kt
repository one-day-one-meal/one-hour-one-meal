package team.sparta.onehouronemeal.domain.course.controller.v1

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
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.CreateCourseRequest
import team.sparta.onehouronemeal.domain.course.dto.v1.UpdateCourseRequest
import team.sparta.onehouronemeal.domain.course.service.v1.CourseService
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@RequestMapping("/api/v1/courses")
@RestController
class CourseController(
    private val courseService: CourseService,
) {

    @GetMapping
    fun getCourseList(): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity.ok(courseService.getCourseList())
    }

    @GetMapping("/following")
    fun getCourseByFollowedChef(
        @AuthenticationPrincipal principal: UserPrincipal
    ): ResponseEntity<List<CourseResponse>> {
        return ResponseEntity.ok(courseService.getCourseByFollowedChef(principal))
    }

    @GetMapping("/{courseId}")
    fun getCourse(@PathVariable courseId: Long): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.getCourse(courseId))
    }

    @PostMapping
    fun createCourse(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: CreateCourseRequest
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(principal, request))
    }

    @PutMapping("/{courseId}")
    fun updateCourse(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable courseId: Long,
        @RequestBody request: UpdateCourseRequest
    ): ResponseEntity<CourseResponse> {
        return ResponseEntity.ok(courseService.updateCourse(principal, courseId, request))
    }

    @DeleteMapping("/{courseId}")
    fun deleteCourse(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable courseId: Long
    ): ResponseEntity<Unit> {
        courseService.deleteCourse(principal, courseId)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{courseId}/thumbs-up")
    fun likeCourse(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable courseId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(courseService.likeCourse(principal, courseId))
    }

    @DeleteMapping("/{courseId}/thumbs-up")
    fun cancelLikeCourse(
        @AuthenticationPrincipal principal: UserPrincipal, @PathVariable courseId: Long
    ): ResponseEntity<Unit> {
        return ResponseEntity.ok(courseService.cancelLikeCourse(principal, courseId))
    }
}