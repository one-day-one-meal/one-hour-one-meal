package team.sparta.onehouronemeal.domain.comment.model.v1.report

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
import team.sparta.onehouronemeal.domain.comment.model.v1.Comment
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity
import team.sparta.onehouronemeal.domain.user.model.v1.User
import java.time.LocalDateTime

@Entity
@Table(name = "report")
@SQLDelete(sql = "UPDATE report SET status = 'DELETED', deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("status = 'ACTIVE'")
class Report(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val comment: Comment? = null,

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    val describe: String,

    @Enumerated(EnumType.STRING)
    var status: ReportStatus = ReportStatus.ACTIVE,

    @Column(nullable = true, updatable = true)
    var deletedAt: LocalDateTime? = null,
) : BaseTimeEntity() {
    init {
        this.validate()
    }

    fun changeStatus(status: ReportStatus) {
        this.status = status
    }

    private fun validate() {
        require(describe.length <= 1000) { "Describe must be less than 1000 characters" }
    }
}