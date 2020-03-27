package fr.agaspardcilia.boilerplate.users

import fr.agaspardcilia.boilerplate.users.dto.UserCreationDto
import fr.agaspardcilia.boilerplate.users.dto.UserDto
import fr.agaspardcilia.boilerplate.users.dto.toDto
import fr.agaspardcilia.boilerplate.users.dto.toEntity
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
open class UserService(
    private val userRepository: UserRepository,
    private val activationKeyRepository: ActivationKeyRepository,
    private val passwordEncoder: PasswordEncoder
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun get(id: UUID): UserDto? = userRepository.findByIdOrNull(id).let { it?.toDto() }

    fun getByMail(mail: String): UserDto? = userRepository.findByMail(mail).let { it?.toDto() }

    fun doesExistByMail(mail: String): Boolean = userRepository.existsByMail(mail)

    fun getAll(): List<UserDto> = userRepository.findAll().map { it.toDto() }

    open fun registerUser(user: UserCreationDto): UserDto {
        val toSave: User = user.toEntity()
        if (doesExistByMail(user.mail)) {
            throw MailAlreadyInUserException()
        }

        toSave.password = passwordEncoder.encode(user.password)
        toSave.authorities.add(Authority.USER)

        val result = userRepository.save(toSave)
        val activationKey = createAndSaveActivationKey(result)

        log.debug("Registering ${user.mail} with ${activationKey.id} as his activation key!")

        return result.toDto()
    }

    fun getCurrentUser(): UserDto? {
        val userMail = getCurrentUserLogin()
        return if (userMail != null) userRepository.findByMail(userMail).let { it?.toDto() } else null
    }

    private fun getCurrentUserLogin(): String? = SecurityContextHolder.getContext().authentication?.name

    fun activateUser(activationKeyId: String) {
        val activationKey = activationKeyRepository.findById(UUID.fromString(activationKeyId))
            .orElseThrow { UserNotFoundException() }
        val user = activationKey.user

        if (user != null) {
            user.isActive = true
            userRepository.save(user)
            activationKeyRepository.delete(activationKey)
        }
    }

    private fun createAndSaveActivationKey(user: User): ActivationKey {
        return activationKeyRepository.save(ActivationKey(user = user))
    }
}
