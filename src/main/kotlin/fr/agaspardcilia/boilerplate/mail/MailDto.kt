package fr.agaspardcilia.boilerplate.mail

data class MailDto (
    val from: String,
    val to: String,
    val subject: String,
    val content: String
)
