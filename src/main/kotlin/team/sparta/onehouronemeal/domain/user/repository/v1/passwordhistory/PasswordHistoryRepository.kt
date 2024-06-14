package team.sparta.onehouronemeal.domain.user.repository.v1.passwordhistory

import team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory.PasswordHistory

interface PasswordHistoryRepository {
    fun save(passwordHistory: PasswordHistory): PasswordHistory
    fun findLastThreePasswordsByUserId(userId: Long): List<String>
    fun deleteByUserId(userId: Long)
    fun deleteByIds(ids: List<Long>)
    fun deleteHistoriesIfCountOver(userId: Long)
}