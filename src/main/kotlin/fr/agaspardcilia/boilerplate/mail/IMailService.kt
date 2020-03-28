package fr.agaspardcilia.boilerplate.mail

import org.springframework.scheduling.annotation.Async

interface IMailService {

    @Async
    fun sendMail(mail: MailDto)
}
