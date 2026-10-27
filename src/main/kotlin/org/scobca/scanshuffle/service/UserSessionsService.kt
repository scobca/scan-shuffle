package org.scobca.scanshuffle.service

import jakarta.transaction.Transactional
import org.scobca.scanshuffle.dto.auth.RotateRefreshTokenDto
import org.scobca.scanshuffle.entity.UserSession
import org.scobca.scanshuffle.repository.UserSessionRepository
import org.springframework.stereotype.Service
import ru.careerseekers.core.exceptions.BadRequestException
import ru.careerseekers.core.exceptions.NotFoundException
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.Duration
import java.time.Instant
import java.util.Base64
import java.util.HexFormat
import java.util.UUID

@Service
class UserSessionsService(private val userSessionRepository: UserSessionRepository) {

    private val secureRandom = SecureRandom()

    @Transactional
    fun create(userId: UUID): String {
        val refreshToken = generateToken()
        val hash = generateHash(refreshToken)

        userSessionRepository.save(
            UserSession(
                userId = userId,
                refreshTokenHash = hash,
                expiresAt = Instant.now().plus(Duration.ofDays(30))
            )
        )

        return refreshToken
    }

    @Transactional
    fun rotate(refreshToken: String): RotateRefreshTokenDto {
        val hash = generateHash(refreshToken)
        val now = Instant.now()

        return userSessionRepository.findByRefreshTokenHash(hash)
            ?.let { userSession ->

                if (userSession.revokedAt != null || !userSession.expiresAt.isAfter(now)) {
                    throw BadRequestException("Session revoked or expired.")
                }

                val newRefreshToken = generateToken()
                userSession.refreshTokenHash = generateHash(newRefreshToken)
                userSession.expiresAt = now.plus(Duration.ofDays(30))
                userSessionRepository.save(userSession)

                RotateRefreshTokenDto(
                    refreshToken = newRefreshToken,
                    userId = userSession.userId
                )
            } ?: throw NotFoundException("Session not found.")

    }

    @Transactional
    fun revoke(refreshToken: String) {
        val hash = generateHash(refreshToken)
        val now = Instant.now()

        userSessionRepository.findByRefreshTokenHash(hash)
            ?.let { userSession ->
                if (userSession.revokedAt == null) {
                    userSession.revokedAt = now
                    userSessionRepository.save(userSession)
                }
            }
    }

    @Transactional
    fun revokeAll(userId: UUID) {
        val now = Instant.now()

        userSessionRepository.findAllByUserIdAndRevokedAtIsNullAndExpiresAtAfter(userId, now)
            .onEach { it.revokedAt = now }
            .let(userSessionRepository::saveAll)
    }

    private fun generateToken(): String {
        val bytes = ByteArray(32)
        secureRandom.nextBytes(bytes)

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes)
    }

    private fun generateHash(token: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(token.toByteArray(StandardCharsets.UTF_8))
            .let { HexFormat.of().formatHex(it) }
    }
}
