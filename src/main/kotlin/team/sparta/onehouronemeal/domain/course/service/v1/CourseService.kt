package team.sparta.onehouronemeal.domain.course.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.comment.dto.v1.CommentResponse
import team.sparta.onehouronemeal.domain.comment.repository.v1.CommentRepository
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponse
import team.sparta.onehouronemeal.domain.course.dto.v1.CourseResponseWithRecipesAndComments
import team.sparta.onehouronemeal.domain.course.dto.v1.CreateCourseRequest
import team.sparta.onehouronemeal.domain.course.dto.v1.UpdateCourseRequest
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUp
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUpId
import team.sparta.onehouronemeal.domain.course.repository.v1.CourseRepository
import team.sparta.onehouronemeal.domain.course.repository.v1.thumbsup.ThumbsUpRepository
import team.sparta.onehouronemeal.domain.recipe.dto.v1.RecipeResponse
import team.sparta.onehouronemeal.domain.recipe.repository.v1.RecipeRepository
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.domain.user.repository.v1.subscription.SubscriptionRepository
import team.sparta.onehouronemeal.exception.AccessDeniedException
import team.sparta.onehouronemeal.exception.ModelNotFoundException
import team.sparta.onehouronemeal.infra.security.UserPrincipal

@Service
class CourseService(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository,
    private val thumbsUpRepository: ThumbsUpRepository,
    private val recipeRepository: RecipeRepository,
    private val subscriptionRepository: SubscriptionRepository,
    private val commentRepository: CommentRepository
) {

    fun getCourseList(): List<CourseResponse> {
        val (openCourseListWithUser, countList) = courseRepository.searchOpenCourseListWithThumbsUpCount()

        return openCourseListWithUser.zip(countList) { a, b ->
            CourseResponse(
                id = a.id!!,
                title = a.title,
                user = UserResponse.from(a.user),
                describe = a.describe,
                status = a.status.name,
                thumbsUpCount = b,
                createdAt = a.createdAt,
                updatedAt = a.updatedAt
            )
        }
    }

    fun getCourseListBySubscribedChefs(principal: UserPrincipal): List<CourseResponse> {
        val (openCourseListBySubscribedChefs, countList) = courseRepository.getOpenCourseListBySubscribedChefs(principal)

        return openCourseListBySubscribedChefs.zip(countList) { a, b ->
            CourseResponse(
                id = a.id!!,
                title = a.title,
                user = UserResponse.from(a.user),
                describe = a.describe,
                status = a.status.name,
                thumbsUpCount = b,
                createdAt = a.createdAt,
                updatedAt = a.updatedAt
            )
        }
    }

    @Transactional(readOnly = true)
    fun getCourse(courseId: Long): CourseResponseWithRecipesAndComments {
        val course =
            courseRepository.findByIdOrNullWithUser(courseId) ?: throw ModelNotFoundException("Course", courseId)

        if (!course.isOpened()) throw IllegalStateException("Course Is Not Opened")

        val recipeList = recipeRepository.findAllByCourseId(courseId)

        val commentList = commentRepository.findAllByCourseId(courseId)

        return CourseResponseWithRecipesAndComments.from(
            course,
            thumbsUpRepository.thumbsUpCount(course.id!!),
            recipeList.map { RecipeResponse.from(it) },
            commentList.map { CommentResponse.from(it) })
    }

    fun createCourse(principal: UserPrincipal, request: CreateCourseRequest): CourseResponse {
        val user = userRepository.findById(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        val course = Course(
            title = request.title, describe = request.describe, user = user
        )

        return CourseResponse.from(courseRepository.save(course))
    }

    @Transactional
    fun updateCourse(principal: UserPrincipal, courseId: Long, request: UpdateCourseRequest): CourseResponse {
        validateUserExists(principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        checkPermission(course, principal)

        course.updateCourse(request.title, request.describe)

        return CourseResponse.from(course, thumbsUpRepository.thumbsUpCount(course.id!!))
    }

    @Transactional
    fun deleteCourse(principal: UserPrincipal, courseId: Long) {
        if (!userRepository.existsById(principal.id)) throw ModelNotFoundException("User", principal.id)

        val course = courseRepository.findByIdOrNull(courseId) ?: throw ModelNotFoundException("Course", courseId)

        checkPermission(course, principal)

        courseRepository.deleteCourseWithRecipesCommentsThumbsUps(courseId)
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
        validateUserExists(principal.id)

        if (!courseRepository.existsById(courseId)) throw ModelNotFoundException("Course", courseId)

        val thumbsUpId = ThumbsUpId(courseId = courseId, userId = principal.id)

        val thumbsUp = thumbsUpRepository.findByIdOrNull(thumbsUpId)
            ?: throw IllegalArgumentException("You've not given a thumbs up to this post, so it can't be canceled")

        thumbsUpRepository.delete(thumbsUp)
    }

    private fun ThumbsUpRepository.thumbsUpCount(courseId: Long): Long {
        return thumbsUpRepository.countByCourseId(courseId)
    }

    private fun validateUserExists(userId: Long) {
        if (!userRepository.existsById(userId)) throw ModelNotFoundException("User", userId)
    }

    private fun checkPermission(course: Course, principal: UserPrincipal) {
        check(
            course.checkPermission(
                principal.id,
                principal.role
            )
        ) { throw AccessDeniedException("You do not own this course") }
    }
}