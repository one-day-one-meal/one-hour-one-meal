package team.sparta.onehouronemeal.domain.user.service.v1.passwordhistory

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import team.sparta.onehouronemeal.domain.user.model.v1.User
import team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory.PasswordHistory
import team.sparta.onehouronemeal.domain.user.repository.v1.passwordhistory.PasswordHistoryRepository

@Service
class PasswordHistoryService(
    private val passwordHistoryRepository: PasswordHistoryRepository,
    private val encoder: PasswordEncoder
) {
    fun checkPasswordChange(userId: Long, newPassword: String) {
        check(
            passwordHistoryRepository
                .findLastThreePasswordsByUserId(userId)
                .none { encoder.matches(newPassword, it) }
        ) { "Password cannot be the same as the last three passwords" }
    }

    fun savePasswordHistory(user: User) {
        passwordHistoryRepository.save(
            PasswordHistory(
                user = user,
                password = user.password
            )
        )

        passwordHistoryRepository.deleteHistoriesIfCountOver(user.id!!)
    }

    fun deleteAllByUserId(userId: Long) {
        passwordHistoryRepository.deleteByUserId(userId)
    }

    fun deleteAllById(ids: List<Long>) {
        passwordHistoryRepository.deleteByIds(ids)
    }
}