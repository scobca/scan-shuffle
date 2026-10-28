package org.scobca.scanshuffle.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class WebSecurityConfig {

    val csrfPaths = setOf(
        "/api/v1/auth/register",
        "/api/v1/auth/login",
        "/api/v1/auth/refresh",
        "/api/v1/auth/logout"
    )

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/auth/register",
                    "/api/v1/auth/login",
                    "/api/v1/auth/refresh",
                    "/api/v1/auth/logout",
                ).permitAll()

                auth.requestMatchers(
                    HttpMethod.GET,
                    "/api/v1/auth/csrf",
                ).permitAll()

                auth.anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 -> oauth2.jwt { } }
            .csrf { csrf ->
                csrf.spa()
                csrf.requireCsrfProtectionMatcher { request ->
                    request.method == "POST" && request.servletPath in csrfPaths
                }
            }
            .cors { cors -> cors.disable() }

        return http.build()
    }
}