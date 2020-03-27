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

//    class Cors {
//        var allowedOrigins: String? = null
//        lateinit var allowedMethods: String
//        lateinit var allowedHeaders: String
//        lateinit var exposedHeaders: String
//        var allowCredentials by Delegates.notNull<Boolean>()
//        var maxAge by Delegates.notNull<Long>()
//    }
}

