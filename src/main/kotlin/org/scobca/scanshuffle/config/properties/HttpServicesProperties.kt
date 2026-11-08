package org.scobca.scanshuffle.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("services")
class HttpServicesProperties {
    lateinit var frontendLocal: String
    lateinit var frontendProduction: String
}