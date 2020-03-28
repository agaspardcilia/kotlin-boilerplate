package fr.agaspardcilia.boilerplate.users.authentication

import fr.agaspardcilia.boilerplate.users.User
import fr.agaspardcilia.boilerplate.users.UserNotActivatedException
import fr.agaspardcilia.boilerplate.users.UserNotFoundException
import fr.agaspardcilia.boilerplate.users.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component("userDetailsService")
open class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    override fun loadUserByUsername(mail: String): UserDetails {
        log.debug("Authenticating {}", mail)
        when (val maybeUser = userRepository.findByMail(mail.toLowerCase())) {
            null -> throw UserNotFoundException()
            else -> return createSpringSecurityUser(maybeUser)
        }
    }

    private fun createSpringSecurityUser(user: User): org.springframework.security.core.userdetails.User {
        if (!user.isActive) {
            throw UserNotActivatedException(user.mail)
        }
        val grantedAuthorities = user.authorities.map { SimpleGrantedAuthority(it.name) }
        return org.springframework.security.core.userdetails.User(
            user.mail!!,
            user.password!!,
            grantedAuthorities
        )
    }

}
