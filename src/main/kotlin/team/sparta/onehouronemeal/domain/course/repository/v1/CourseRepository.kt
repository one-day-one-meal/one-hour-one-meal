package team.sparta.onehouronemeal.domain.course.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import team.sparta.onehouronemeal.domain.course.model.v1.Course

interface CourseRepository : JpaRepository<Course, Long>, CustomCourseRepository