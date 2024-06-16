package team.sparta.onehouronemeal.domain.comment.model.v1

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.user.model.v1.User

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JoinColumn(name = "course_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val course: Course,

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    var content: String,
) : BaseTimeEntity() {
    init {
        this.validate()
    }

    fun checkPermission(userId: Long, role: String): Boolean {
        return this.user.id == userId || role == "ROLE_ADMIN"
    }

    fun updateComment(content: String) {
        this.content = content
        
        this.validate()
    }

    private fun validate() {
        require(content.length <= 1000) { "Content must be less than 1000 characters" }
    }
}