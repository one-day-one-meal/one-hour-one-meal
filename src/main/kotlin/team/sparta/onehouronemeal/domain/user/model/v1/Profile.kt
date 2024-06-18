package team.sparta.onehouronemeal.domain.user.model.v1

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Lob
import jakarta.validation.constraints.Size

@Embeddable
data class Profile(
    @Column(name = "nickname") @Size(max = 50) val nickname: String,

    @Column(name = "profile_image_url") @Lob val profileImageUrl: String? = null,
)