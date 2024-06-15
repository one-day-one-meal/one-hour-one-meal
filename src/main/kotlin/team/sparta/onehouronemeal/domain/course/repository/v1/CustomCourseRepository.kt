package team.sparta.onehouronemeal.domain.course.repository.v1

import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.infra.security.UserPrincipal

interface CustomCourseRepository {
    fun searchOpenCourseListWithThumbsUpCount(): Pair<List<Course>, List<Long>>
    fun findByIdOrNullWithUser(courseId: Long): Course?
    fun deleteCourseWithRecipesCommentsThumbsUps(courseId: Long)
    fun getOpenCourseListBySubscribedChefs(principal: UserPrincipal): Pair<List<Course>, List<Long>>
    fun findAllPendingCourse(): List<Course>
}