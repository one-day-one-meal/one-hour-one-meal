package team.sparta.onehouronemeal.domain.auth.common

import team.sparta.onehouronemeal.infra.security.UserPrincipal

object PermissionChecker {
    fun check(entityOwnerId: Long, principal: UserPrincipal) {
        val hasPermission = entityOwnerId == principal.id || principal.isAdmin()
        check(hasPermission) { "Permission denied" }
    }
}