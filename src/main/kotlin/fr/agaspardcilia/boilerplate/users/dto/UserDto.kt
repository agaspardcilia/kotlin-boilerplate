package fr.agaspardcilia.boilerplate.users.dto

import fr.agaspardcilia.boilerplate.users.User
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class UserDto(
    val id: UUID?,

    @field:Email
    @field:Size(min = 5, max = 254)
    val mail: String?,

    @field:Size(min = 5, max = 254)
    val firstname: String?,

    @field:Size(min = 5, max = 254)
    val lastname: String?,

    val isActive: Boolean
)

fun User.toDto() = UserDto(this.id, this.mail, this.firstname, this.lastname, this.isActive)
fun UserDto.toEntity() = User(id = this.id, mail = this.mail, firstname = this.firstname, lastname = this.lastname, isActive = this.isActive)
