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
    @Column(name = "username", unique = true)
    @Size(max = 100)
    val username: String,

    @Column(name = "password")
    @Size(max = 100)
    var password: String,

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

    init {
        this.validate()
    }

    fun checkPermission(userId: Long, role: String): Boolean {
        return this.id == userId || role == "ROLE_ADMIN"
    }

    fun updatePassword(password: String) {
        this.password = password

        this.validate()
    }

    fun updateProfile(profile: Profile) {
        this.profile = profile

        this.validate()
    }

    fun changeStatus(status: UserStatus) {
        this.status = status

        this.validate()
    }

    fun isActive(): Boolean {
        return this.status == UserStatus.ACTIVE
    }

    private fun validate() {
        require(username.length < 100) { "Username must be less than 100 characters" }
        require(password.length < 100) { "Password must be less than 100 characters" }
        require(provider == null || provider!!.length < 20) { "Provider must be less than 20 characters" }
        require(providerId == null || providerId!!.length < 255) { "ProviderId must be less than 255 characters" }
    }
}
