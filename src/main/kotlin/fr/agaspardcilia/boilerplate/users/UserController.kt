package fr.agaspardcilia.boilerplate.users

import fr.agaspardcilia.boilerplate.users.dto.PasswordChangeDto
import fr.agaspardcilia.boilerplate.users.dto.UserCreationDto
import fr.agaspardcilia.boilerplate.users.dto.UserDto
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/{id}")
    fun get(@PathVariable id: UUID): UserDto? {
        log.info("Getting user {}", id)
        return userService.get(id)
    }

    @GetMapping
    fun getAll(): List<UserDto> {
        log.info("Getting all users")
        return userService.getAll()
    }

    @GetMapping("/mail/{mail}")
    fun getByMail(@PathVariable mail: String): UserDto? {
        log.info("Getting user with mail {}", mail)
        return userService.getByMail(mail)
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody user: UserCreationDto): UserDto {
        log.info("Registering user {}", user.mail)
        return userService.registerUser(user)
    }

    @GetMapping("/current-user")
    fun getCurrentUser(): UserDto? {
        log.info("Getting current user")
        return userService.getCurrentUser()
    }

    @PutMapping("/activate/{key}")
    fun activateUser(@PathVariable key: String) {
        log.info("Activating user with key {}", key)
        userService.activateUser(key)
    }

    @PutMapping("/password")
    fun changePassword(@RequestBody @Valid passwordChangeDto: PasswordChangeDto) {
        log.info("Attempting to change ${passwordChangeDto.username}'s password")
        userService.changePassword(passwordChangeDto)
    }
}
