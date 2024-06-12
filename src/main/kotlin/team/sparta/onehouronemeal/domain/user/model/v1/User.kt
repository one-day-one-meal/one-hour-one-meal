package team.sparta.onehouronemeal.domain.user.model.v1

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "app_user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "nickname")
    val nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole = UserRole.STUDENT,
)
