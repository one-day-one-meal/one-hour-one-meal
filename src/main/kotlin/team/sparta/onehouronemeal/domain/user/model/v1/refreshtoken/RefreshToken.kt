package team.sparta.onehouronemeal.domain.user.model.v1.refreshtoken

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import team.sparta.onehouronemeal.domain.user.model.v1.User

@Entity
@Table(name = "refresh_token")
data class RefreshToken(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val userId: Long,

    @Column(name = "refresh_token", nullable = false)
    var refreshToken: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE])
    @MapsId
    @JoinColumn(name = "user_id")
    val user: User
) {
    fun updateToken(token: String) {
        refreshToken = token
    }
}