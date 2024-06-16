package team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import org.hibernate.annotations.CreationTimestamp
import team.sparta.onehouronemeal.domain.user.model.v1.User
import java.time.LocalDateTime

@Entity
@Table(name = "password_history")
data class PasswordHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(name = "password")
    @Size(max = 50)
    val password: String,

    @CreationTimestamp
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)