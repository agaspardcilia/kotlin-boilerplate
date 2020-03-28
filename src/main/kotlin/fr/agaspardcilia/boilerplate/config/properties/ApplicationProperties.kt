package fr.agaspardcilia.boilerplate.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
class ApplicationProperties {
    var security: Security = Security()
    var mail: Mail = Mail()
}
