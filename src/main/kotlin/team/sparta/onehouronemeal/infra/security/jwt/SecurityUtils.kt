package team.sparta.onehouronemeal.infra.security.jwt

import org.springframework.security.core.context.SecurityContextHolder
import team.sparta.onehouronemeal.infra.security.UserPrincipal

object SecurityUtils {
    fun getCurrentUserIdOrNull(): Long? {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as? UserPrincipal
        return principal?.id
    }

    fun getCurrentUserRole(): String {
        val authentication = SecurityContextHolder.getContext().authentication
        val principal = authentication.principal as? UserPrincipal
        return principal?.authorities?.firstOrNull()?.authority ?: "ROLE_ANONYMOUS"
    }

    fun hasPermission(userId: Long?): Boolean {
        val currentUserId = getCurrentUserIdOrNull() ?: return false
        if (getCurrentUserRole() == "ROLE_ADMIN") return true
        return currentUserId == userId
    }
}