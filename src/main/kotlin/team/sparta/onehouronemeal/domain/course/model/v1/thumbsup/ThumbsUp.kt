package team.sparta.onehouronemeal.domain.course.model.v1.thumbsup

import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.user.model.v1.User
import java.io.Serializable

@Entity
@Table(name="thumbs_up")
class ThumbsUp(
    @EmbeddedId
    val id: ThumbsUpId,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    val course: Course,

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    val user: User
)

@Embeddable
data class ThumbsUpId(
    val courseId: Long,
    val userId: Long
) : Serializable


