package team.sparta.onehouronemeal.domain.admin.service.v1

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import team.sparta.onehouronemeal.domain.user.dto.v1.UserResponse
import team.sparta.onehouronemeal.domain.user.model.v1.UserStatus
import team.sparta.onehouronemeal.domain.user.repository.v1.UserRepository
import team.sparta.onehouronemeal.exception.ModelNotFoundException

@Service
class AdminService(
    private val userRepository: UserRepository,
) {
    fun getPendingUserList(): List<UserResponse> {
        return userRepository.findByStatusOrderByCreatedAtDesc(UserStatus.PENDING).map { UserResponse.from(it) }
    }

    @Transactional
    fun confirmSignUp(userId: Long) {
        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("user", userId)
        if (user.status != UserStatus.PENDING) throw IllegalStateException("해당 사용자는 승인 대기 중 상태가 아닙니다.")
        user.changeStatus(UserStatus.ACTIVE)
    }

    @Transactional
    fun rejectSignUp(userId: Long) {
        val user = userRepository.findById(userId) ?: throw ModelNotFoundException("user", userId)
        if (user.status != UserStatus.PENDING) throw IllegalStateException("해당 사용자는 승인 대기 중 상태가 아닙니다.")
        user.changeStatus(UserStatus.DENIED)
    }
}
