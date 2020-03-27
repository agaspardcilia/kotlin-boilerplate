package fr.agaspardcilia.boilerplate.users.dto

data class LoginDto(
    val username: String,
    val password: String,
    val rememberMe: Boolean = false
)
