package org.scobca.scanshuffle.repository

import jakarta.persistence.LockModeType
import org.scobca.scanshuffle.entity.UserSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface UserSessionRepository : JpaRepository<UserSession, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByRefreshTokenHash(refreshTokenHash: String): UserSession?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllByUserIdAndRevokedAtIsNullAndExpiresAtAfter(
        userId: UUID,
        now: Instant
    ): List<UserSession>
}
