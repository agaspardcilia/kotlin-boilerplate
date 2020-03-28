package fr.agaspardcilia.boilerplate.mail

import org.springframework.context.annotation.Profile
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Profile("mail")
@Service
open class MailServiceImplementation(
    val javaMailSender: JavaMailSender
) : IMailService {

    override fun sendMail(mail: MailDto) {
        TODO("Not yet implemented")
    }

}
