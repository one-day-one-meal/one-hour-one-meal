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
import team.sparta.onehouronemeal.domain.user.model.v1.User

@Entity
@Table(name = "comment")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val courseId: Long,

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    val content: String,

) : BaseTimeEntity()