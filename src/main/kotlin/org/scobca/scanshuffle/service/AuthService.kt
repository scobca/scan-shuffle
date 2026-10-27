package org.scobca.scanshuffle.service

import org.scobca.scanshuffle.dto.auth.AuthResultDto
import org.scobca.scanshuffle.dto.auth.LoginUserDto
import org.scobca.scanshuffle.dto.auth.RegisterUserDto
import org.scobca.scanshuffle.repository.UserRepository
import org.scobca.scanshuffle.util.AccessTokenIssuer
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.careerseekers.core.exceptions.UnauthorizedException
import java.util.UUID

@Service
class AuthService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val accessTokenIssuer: AccessTokenIssuer,
    private val userSessionsService: UserSessionsService,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun register(dto: RegisterUserDto): AuthResultDto {
        return userService.create(dto.toCreateUserDto())
            .let { user ->
                AuthResultDto(
                    accessToken = accessTokenIssuer.create(user.id),
                    refreshToken = userSessionsService.create(user.id)
                )
            }
    }

    @Transactional
    fun login(dto: LoginUserDto): AuthResultDto {
        return userRepository.findByEmail(dto.email)
            ?.let { user ->
                if (passwordEncoder.matches(dto.password, user.password)) {
                    AuthResultDto(
                        accessToken = accessTokenIssuer.create(user.id!!),
                        refreshToken = userSessionsService.create(user.id!!)
                    )
                } else throw UnauthorizedException("Неверный логин или пароль.")
            }
            ?: throw UnauthorizedException("Неверный логин или пароль.")
    }

    @Transactional
    fun refresh(refreshToken: String): AuthResultDto {
        return userSessionsService.rotate(refreshToken)
            .let { rotated ->
                AuthResultDto(
                    accessToken = accessTokenIssuer.create(rotated.userId),
                    refreshToken = rotated.refreshToken
                )
            }
    }

    @Transactional
    fun logout(refreshToken: String) {
        return userSessionsService.revoke(refreshToken)
    }

    @Transactional
    fun logoutFromAllSessions(userId: UUID) {
        return userSessionsService.revokeAll(userId)
    }
}