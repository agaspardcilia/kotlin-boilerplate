package fr.agaspardcilia.boilerplate.users.dto

import fr.agaspardcilia.boilerplate.users.User
import fr.agaspardcilia.boilerplate.utils.MAX_PASSWORD_SIZE
import fr.agaspardcilia.boilerplate.utils.MIN_PASSWORD_SIZE
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserCreationDto(
    @field:Email
    @field:Size(min = 5, max = 254)
    @field:NotNull
    val mail: String?,

    @field:Size(min = MIN_PASSWORD_SIZE, max = MAX_PASSWORD_SIZE)
    @field:NotNull
    val password: String?,

    @field:Size(min = 5, max = 254)
    @field:NotNull
    val firstname: String?,

    @field:Size(min = 5, max = 254)
    @field:NotNull
    val lastname: String?
)

fun UserCreationDto.toEntity() = User(mail = this.mail, firstname = this.firstname, lastname = this.lastname)

