package team.sparta.onehouronemeal.infra.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

data class UserPrincipal(
    val id: Long,
    val authorities: Collection<GrantedAuthority>
) {
    constructor(id: Long, roles: Set<String>) : this(
        id,
        roles.map { SimpleGrantedAuthority("ROLE_$it") }
    )

    val role: String = authorities.first().authority ?: "ROLE_UNKNOWN"

    fun isAdmin(): Boolean {
        return authorities.any { it.authority == "ROLE_ADMIN" }
    }
}
