package team.sparta.onehouronemeal.domain.comment.model.v1.report

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import team.sparta.onehouronemeal.domain.comment.model.v1.Comment
import team.sparta.onehouronemeal.domain.user.model.v1.User
import java.time.LocalDateTime

@Entity
@Table(name = "report")
class Report(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val comment: Comment,

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    val describe: String,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime? = null
)