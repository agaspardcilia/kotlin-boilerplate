package fr.agaspardcilia.boilerplate.users.authentication

import fr.agaspardcilia.boilerplate.users.AuthenticationException
import fr.agaspardcilia.boilerplate.users.UserNotActivatedException
import fr.agaspardcilia.boilerplate.users.UserService
import fr.agaspardcilia.boilerplate.users.dto.LoginDto
import fr.agaspardcilia.boilerplate.users.dto.UserDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class JWTAuthenticationService(
    private val tokenProvider: TokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val userService: UserService
) {

    @Throws(AuthenticationException::class)
    fun authenticateWithJWT(loginDto: LoginDto): String {
        val authentication: Authentication = this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(loginDto.username, loginDto.password))
        when (val user: UserDto? = userService.getByMail(loginDto.username!!)) {
            null -> throw AuthenticationException("Wrong username/password combination!")
            else -> {
                if (!user.isActive) {
                    throw UserNotActivatedException(user.mail)
                }
                SecurityContextHolder.getContext().authentication = authentication
                return tokenProvider.createToken(authentication, loginDto.rememberMe)
            }
        }


    }
}
