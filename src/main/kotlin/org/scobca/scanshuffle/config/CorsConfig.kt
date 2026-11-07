package org.scobca.scanshuffle.config

import org.scobca.scanshuffle.config.properties.HttpServicesProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(private val serviceProperties: HttpServicesProperties) {

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val corsConfig = CorsConfiguration().apply {
            addAllowedOrigin(serviceProperties.frontendLocal)
            addAllowedOrigin(serviceProperties.frontendProduction)

            allowedMethods = listOf("*")
            allowedHeaders = listOf(
                "Content-Type",
                "Authorization",
                "Accept",
                "Origin",
                "X-Requested-With",
                "X-XSRF-TOKEN",
                "User-Email",
                "sentry-trace",
                "baggage",
            )
            allowCredentials = true
        }

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }
}
