package org.scobca.scanshuffle.controller

import jakarta.validation.Valid
import org.scobca.scanshuffle.dto.auth.LoginUserDto
import org.scobca.scanshuffle.dto.auth.RegisterUserDto
import org.scobca.scanshuffle.service.AuthService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.careerseekers.core.api.BasicSuccessfulResponse
import java.time.Duration
import java.util.UUID

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(private val authService: AuthService) {

    @GetMapping("/csrf")
    fun csrf(): ResponseEntity<Void> {
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody dto: RegisterUserDto): ResponseEntity<BasicSuccessfulResponse<String>> {
        val tokens = authService.register(dto)
        val cookie = createCookie(tokens.refreshToken)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(BasicSuccessfulResponse(tokens.accessToken))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody dto: LoginUserDto): ResponseEntity<BasicSuccessfulResponse<String>> {
        val tokens = authService.login(dto)
        val cookie = createCookie(tokens.refreshToken)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(BasicSuccessfulResponse(tokens.accessToken))
    }

    @PostMapping("/refresh")
    fun refresh(@CookieValue("refresh_token") refreshToken: String): ResponseEntity<BasicSuccessfulResponse<String>> {
        val tokens = authService.refresh(refreshToken)
        val cookie = createCookie(tokens.refreshToken)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(BasicSuccessfulResponse(tokens.accessToken))
    }

    @PostMapping("/logout")
    fun logout(
        @CookieValue(name = "refresh_token", required = false) refreshToken: String?
    ): ResponseEntity<Void> {
        refreshToken?.let(authService::logout)

        return noContentWithClearedCookie()
    }

    @PostMapping("/logoutFromAllSessions")
    fun logoutFromAllSessions(@AuthenticationPrincipal jwt: Jwt): ResponseEntity<Void> {
        authService.logoutFromAllSessions(UUID.fromString(jwt.subject))

        return noContentWithClearedCookie()
    }

    private fun createCookie(refreshToken: String): ResponseCookie {
        return ResponseCookie.from("refresh_token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/api/v1/auth")
            .maxAge(Duration.ofDays(30))
            .build()
    }

    private fun noContentWithClearedCookie(): ResponseEntity<Void> {
        val cookie = ResponseCookie.from("refresh_token", "")
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/api/v1/auth")
            .maxAge(Duration.ZERO)
            .build()

        return ResponseEntity.noContent()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .build()
    }
}