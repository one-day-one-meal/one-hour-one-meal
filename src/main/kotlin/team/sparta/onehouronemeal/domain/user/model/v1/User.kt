package team.sparta.onehouronemeal.domain.user.model.v1

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.Size
import team.sparta.onehouronemeal.domain.common.BaseTimeEntity

@Entity
@Table(name = "app_user")
class User(
    @Column(name = "username")
    @Size(max = 30)
    val username: String,

    @Column(name = "password")
    @Size(max = 30)
    val password: String,

    @Embedded
    var profile: Profile,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: UserRole = UserRole.STUDENT,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: UserStatus = UserStatus.ACTIVE,

    @Column(name = "provider")
    @Size(max = 20)
    var provider: String? = null,

    @Column(name = "provider_id")
    @Size(max = 255)
    var providerId: String? = null,
) : BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun updateProfile(profile: Profile) {
        this.profile = profile
    }
}
