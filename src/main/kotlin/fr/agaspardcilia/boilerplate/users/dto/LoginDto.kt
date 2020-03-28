package fr.agaspardcilia.boilerplate.users.dto

import javax.validation.constraints.NotNull

data class LoginDto(
    @field:NotNull
    val username: String?,

    @field:NotNull
    val password: String?,

    val rememberMe: Boolean = false
)
