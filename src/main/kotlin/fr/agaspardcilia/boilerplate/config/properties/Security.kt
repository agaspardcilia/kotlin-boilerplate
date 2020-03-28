package fr.agaspardcilia.boilerplate.config.properties

import org.springframework.web.cors.CorsConfiguration
import kotlin.properties.Delegates

class Security {
    var jwt: Jwt = Jwt()
    var cors: CorsConfiguration = CorsConfiguration()

    class Jwt {
        lateinit var secret: String
        var tokenValidityInSeconds by Delegates.notNull<Long>()
        var tokenValidityInSecondsForRememberMe by Delegates.notNull<Long>()
    }
}

