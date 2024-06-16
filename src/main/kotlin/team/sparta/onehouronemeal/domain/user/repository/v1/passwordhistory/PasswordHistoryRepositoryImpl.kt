package team.sparta.onehouronemeal.domain.user.repository.v1.passwordhistory

import org.springframework.stereotype.Repository
import team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory.PasswordHistory
import team.sparta.onehouronemeal.domain.user.model.v1.passwordhistory.QPasswordHistory
import team.sparta.onehouronemeal.infra.querydsl.QueryDslSupport

@Repository
class PasswordHistoryRepositoryImpl(
    private val passwordHistoryJpaRepository: PasswordHistoryJpaRepository
) : PasswordHistoryRepository, QueryDslSupport() {
    val passwordHistory = QPasswordHistory.passwordHistory

    override fun save(passwordHistory: PasswordHistory): PasswordHistory {
        return passwordHistoryJpaRepository.save(passwordHistory)
    }

    override fun findLastThreePasswordsByUserId(userId: Long): List<String> {
        return queryFactory
            .select(passwordHistory.password)
            .from(passwordHistory)
            .where(passwordHistory.user.id.eq(userId))
            .orderBy(passwordHistory.createdAt.desc())
            .limit(3)
            .fetch()
    }

    override fun deleteByUserId(userId: Long) {
        passwordHistoryJpaRepository.deleteAllByUserId(userId)
    }

    override fun deleteByIds(ids: List<Long>) {
        passwordHistoryJpaRepository.deleteAllById(ids)
    }

    override fun deleteHistoriesIfCountOver(userId: Long) {
        val deleteIds = queryFactory
            .select(passwordHistory.id)
            .from(passwordHistory)
            .where(passwordHistory.user.id.eq(userId))
            .orderBy(passwordHistory.createdAt.desc())
            .offset(3)
            .fetch()

        if (deleteIds.isNotEmpty()) {
            queryFactory
                .delete(passwordHistory)
                .where(passwordHistory.id.`in`(deleteIds))
                .execute()
        }
    }
}