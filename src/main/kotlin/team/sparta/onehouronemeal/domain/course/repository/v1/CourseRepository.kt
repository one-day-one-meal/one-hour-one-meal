package team.sparta.onehouronemeal.domain.course.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.course.model.v1.CourseStatus

interface CourseRepository : JpaRepository<Course, Long>, CustomCourseRepository {
    fun findAllByStatusIsOrderByCreatedAtDesc(courseStatus: CourseStatus): List<Course>
}