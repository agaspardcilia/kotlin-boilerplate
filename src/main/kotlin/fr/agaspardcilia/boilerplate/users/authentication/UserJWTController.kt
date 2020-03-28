package fr.agaspardcilia.boilerplate.users.authentication

import com.fasterxml.jackson.annotation.JsonProperty
import fr.agaspardcilia.boilerplate.users.dto.LoginDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api")
class UserJWTController(
    private val jwtAuthenticationService: JWTAuthenticationService
) {

    @PostMapping("/authenticate")
    fun authorize(@Valid @RequestBody login: LoginDto): ResponseEntity<JWTToken> {
        val jwt = jwtAuthenticationService.authenticateWithJWT(login)
        val httpHeaders = HttpHeaders()

        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer $jwt")
        return ResponseEntity(JWTToken(jwt), httpHeaders, HttpStatus.OK)
    }

    class JWTToken(@get:JsonProperty("id_token") var idToken: String?)
}
