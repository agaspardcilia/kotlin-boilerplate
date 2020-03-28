package fr.agaspardcilia.boilerplate.users.dto

import javax.validation.constraints.NotNull

data class PasswordChangeDto(
    @field:NotNull
    val username: String,

    @field:NotNull
    val oldPassword: String,

    @field:NotNull
    val newPassword: String
)
