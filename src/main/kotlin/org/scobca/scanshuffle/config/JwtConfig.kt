package org.scobca.scanshuffle.config

import org.scobca.scanshuffle.config.properties.JwtProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.util.Base64
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
class JwtConfig(private val jwtProperties: JwtProperties) {

    @Bean
    fun jwtKey(): SecretKey {
        val bytes = Base64.getDecoder().decode(jwtProperties.secretBase64)
        require(bytes.size >= 32) { "JWT key must contain at least 32 bytes" }

        return SecretKeySpec(bytes, "HmacSHA256")
    }

    @Bean
    fun jwtEncoder(key: SecretKey): JwtEncoder {
        return NimbusJwtEncoder.withSecretKey(key)
            .algorithm(MacAlgorithm.HS256)
            .build()
    }

    @Bean
    fun jwtDecoder(key: SecretKey): JwtDecoder {
        return NimbusJwtDecoder.withSecretKey(key)
            .macAlgorithm(MacAlgorithm.HS256)
            .build()
    }
}
