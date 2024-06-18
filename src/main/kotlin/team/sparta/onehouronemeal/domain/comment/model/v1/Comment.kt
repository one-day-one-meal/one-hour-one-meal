package team.sparta.onehouronemeal.domain.comment.model.v1

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity
import team.sparta.onehouronemeal.domain.course.model.v1.Course
import team.sparta.onehouronemeal.domain.user.model.v1.User
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
@SQLDelete(sql = "UPDATE comment SET status = 'DELETED', deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
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

    @Enumerated(EnumType.STRING)
    var status: CommentStatus = CommentStatus.ACTIVE,

    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null,
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

    fun changeStatus(status: CommentStatus) {
        this.status = status
    }

    private fun validate() {
        require(content.length <= 1000) { "Content must be less than 1000 characters" }
    }
}