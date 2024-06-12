package team.sparta.onehouronemeal.domain.course.service.v1

import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.CreateCourseRequest
import team.sparta.onehouronemeal.domain.course.dto.v1.UpdateCourseRequest
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CourseService(
    private val courseRepository: CourseRepository, private val userRepository: UserRepository
) {

    fun getCourseList(): List<CourseResponse> {
        // 모든 코스 목록을 조회

        // 코스 목록 중에 OPEN 인 것만 CourseResponse 로 변환에서 반환
        TODO()
    }

    fun getCourseByFollowedChef(principal: UserPrincipal): List<CourseResponse> {
        // 팔로우한 셰프의 아이디들을 조회

        // 팔로우한 셰프들들에 대한 코스를 조회하여 OPEN 인 것만 CourseResponse 로 변환하여 반환
        TODO()
    }

    fun getCourse(courseId: Long): CourseResponse {
        // 코스 아이디를 가지고 Course 를 조회

        // 코스가 OPEN 인 경우에만 CourseResponse 로 변환하여 반환(?)
        TODO()
    }

    fun createCourse(principal: UserPrincipal, request: CreateCourseRequest): CourseResponse {
        // UserRepository 에서 user 조회

        // CreateCourseRequest 로 Course 생성 후 저장

        TODO()
    }

    fun updateCourse(principal: UserPrincipal, courseId: Long, request: UpdateCourseRequest): CourseResponse {
        // User 본인 확인

        // Course 조회 후 값 변경

        TODO()
    }

    fun deleteCourse(principal: UserPrincipal, courseId: Long) {
        // User 본인 확인

        // Course 삭제

        TODO()
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