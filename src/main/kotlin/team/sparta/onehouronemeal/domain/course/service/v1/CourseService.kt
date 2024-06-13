package team.sparta.onehouronemeal.domain.course.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponseWithRecipes
import team.sparta.onehouronemeal.domain.course.dto.v1.CreateCourseRequest
import team.sparta.onehouronemeal.domain.course.dto.v1.UpdateCourseRequest
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUp
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUpId
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.course.repository.v1.thumbsup.ThumbsUpRepository
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.repository.v1.RecipeRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.AccessDeniedException
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository,
    private val thumbsUpRepository: ThumbsUpRepository,
    private val recipeRepository: RecipeRepository
) {

    private fun ThumbsUpRepository.thumbsUpCount(courseId: Long): Int {
        return thumbsUpRepository.countByCourseId(courseId)
    }

    @Transactional(readOnly = true)
    fun getCourseList(): List<CourseResponse> {
        val courseList = courseRepository.findAllByStatusIsOrderByCreatedAtDesc(CourseStatus.OPEN)

        return courseList.map { CourseResponse.from(it, thumbsUpRepository.thumbsUpCount(it.id!!)) }
    }

    fun getCourseByFollowedChef(principal: UserPrincipal): List<CourseResponse> {
        // 팔로우한 셰프의 아이디들을 조회

        // 팔로우한 셰프들들에 대한 코스를 조회하여 OPEN 인 것만 CourseResponse 로 변환하여 반환
        TODO()
    }

    @Transactional
    fun getCourse(courseId: Long): CourseResponseWithRecipes {
        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        if (!course.isOpened()) throw IllegalStateException("Course Is Not Opened")

        val recipeList = recipeRepository.findAllByCourseId(courseId)

        return CourseResponseWithRecipes.from(
            course,
            thumbsUpRepository.thumbsUpCount(course.id!!),
            recipeList.map { RecipeResponse.from(it) })
    }

    fun createCourse(principal: UserPrincipal, request: CreateCourseRequest): CourseResponse {
        val user = userRepository.findById(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        val course = Course(
            title = request.title, describe = request.describe, user = user
        )

        return CourseResponse.from(courseRepository.save(course), thumbsUpRepository.thumbsUpCount(course.id!!))
    }

    @Transactional
    fun updateCourse(principal: UserPrincipal, courseId: Long, request: UpdateCourseRequest): CourseResponse {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        if (course.user.id != principal.id) throw AccessDeniedException("You do not own this course")

        course.updateCourse(request.title, request.describe)

        return CourseResponse.from(course, thumbsUpRepository.thumbsUpCount(course.id!!))
    }

    @Transactional
    fun deleteCourse(principal: UserPrincipal, courseId: Long) {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        if (course.user.id != principal.id) throw AccessDeniedException("You do not own this course")

        courseRepository.delete(course)
    }

    fun thumbsUpCourse(principal: UserPrincipal, courseId: Long) {
        val thumbsUpId = ThumbsUpId(courseId = courseId, userId = principal.id)

        if (thumbsUpRepository.existsById(thumbsUpId)) throw IllegalArgumentException("You've already given a thumbs up to this post")

        val user = userRepository.findById(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        val thumbsUp = ThumbsUp(
            id = thumbsUpId, course = course, user = user
        )

        thumbsUpRepository.save(thumbsUp)
    }

    @Transactional
    fun cancelThumbsUpCourse(principal: UserPrincipal, courseId: Long) {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)

        val thumbsUpId = ThumbsUpId(courseId = courseId, userId = principal.id)

        val thumbsUp = thumbsUpRepository.findByIdOrNull(thumbsUpId)
            ?: throw IllegalArgumentException("You've not given a thumbs up to this post, so it can't be canceled")

        thumbsUpRepository.delete(thumbsUp)
    }
}