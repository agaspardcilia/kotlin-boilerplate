package fr.agaspardcilia.boilerplate.users.dto

import fr.agaspardcilia.boilerplate.users.User
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserCreationDto(
    @field:Email
    @field:Size(min = 5, max = 254)
    @field:NotNull
    val mail: String,

    @field:Size(min = 5, max = 63)
    @field:NotNull
    val password: String,

    @field:Size(min = 5, max = 254)
    @field:NotNull
    val firstname: String,

    @field:Size(min = 5, max = 254)
    @field:NotNull
    val lastname: String
)

fun UserCreationDto.toEntity() = User(mail = this.mail, firstname = this.firstname, lastname = this.lastname)

