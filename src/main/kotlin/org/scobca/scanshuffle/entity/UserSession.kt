package org.scobca.scanshuffle.entity

import com.github.f4b6a3.uuid.UuidCreator
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.PrePersist
import jakarta.persistence.Table
import org.scobca.scanshuffle.util.TrimEntityListener
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "user_sessions")
@EntityListeners(AuditingEntityListener::class, TrimEntityListener::class)
class UserSession(
    @Id
    var sessionId: UUID? = null,

    @Column(nullable = false)
    var userId: UUID,

    @Column(nullable = false)
    var refreshTokenHash: String,

    @Column(nullable = false)
    var expiresAt: Instant,

    @Column(name = "revoked_at")
    var revokedAt: Instant? = null,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: Instant? = null
) {
    @PrePersist
    fun generateId() {
        if (sessionId == null) {
            sessionId = UuidCreator.getTimeOrderedEpoch()
        }
    }
}