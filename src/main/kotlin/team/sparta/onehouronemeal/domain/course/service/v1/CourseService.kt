package team.sparta.onehouronemeal.domain.course.service.v1

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.CreateCourseRequest
import team.sparta.onehouronemeal.domain.course.dto.v1.UpdateCourseRequest
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CourseService(
    private val courseRepository: CourseRepository, private val userRepository: UserRepository
) {

    @Transactional
    fun getCourseList(): List<CourseResponse> {
        val courseList = courseRepository.findAllByStatusIsOrderByCreatedAtDesc(CourseStatus.OPEN)

        return courseList.map { CourseResponse.from(it, it.user.profile.nickname) }
    }

    fun getCourseByFollowedChef(principal: UserPrincipal): List<CourseResponse> {
        // 팔로우한 셰프의 아이디들을 조회

        // 팔로우한 셰프들들에 대한 코스를 조회하여 OPEN 인 것만 CourseResponse 로 변환하여 반환
        TODO()
    }

    @Transactional
    fun getCourse(courseId: Long): CourseResponse {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        // 예외 추후에 수정 필요
        if (!course.isOpened()) throw RuntimeException("Course Is Not Opened")

        return CourseResponse.from(course, course.user.profile.nickname)
    }

    fun createCourse(principal: UserPrincipal, request: CreateCourseRequest): CourseResponse {
        val user = userRepository.findById(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        val course = Course(
            title = request.title, describe = request.describe, user = user
        )

        return CourseResponse.from(courseRepository.save(course), course.user.profile.nickname)
    }

    @Transactional
    fun updateCourse(principal: UserPrincipal, courseId: Long, request: UpdateCourseRequest): CourseResponse {
        if(!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        // 예외 추후에 수정 필요
        if (course.user.id != principal.id) throw IllegalStateException("Unauthorized")

        course.updateCourse(request.title, request.describe)

        return CourseResponse.from(course, course.user.profile.nickname)
    }

    @Transactional
    fun deleteCourse(principal: UserPrincipal, courseId: Long) {
        if(!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        // 예외 추후에 수정 필요
        if (course.user.id != principal.id) throw IllegalStateException("Unauthorized")

        courseRepository.delete(course)
    }

    fun likeCourse(principal: UserPrincipal, courseId: Long) {
        // User 본인 확인?

        // Like 엔티티 생성 및 저장

        // LikeService 를 따로 만들것인가?에 대해 고민해봐야함

        TODO()
    }

    fun cancelLikeCourse(principal: UserPrincipal, courseId: Long) {
        // User 본인 확인?

        // Like 조회 후 삭제

        // LikeService 를 따로 만들것인가?에 대해 고민해봐야함

        TODO()
    }
}