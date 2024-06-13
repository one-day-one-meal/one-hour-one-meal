package team.sparta.onehouronemeal.domain.course.repository.v1.thumbsup

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUp
import team.sparta.onehouronemeal.domain.course.model.v1.thumbsup.ThumbsUpId

@Repository
interface ThumbsUpRepository: JpaRepository<ThumbsUp, ThumbsUpId> {
    fun countByCourseId(courseId: Long): Int
}