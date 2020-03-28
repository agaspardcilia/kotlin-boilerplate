package fr.agaspardcilia.boilerplate.mail

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("!mail")
@Service
class MockMailService : IMailService {

    private val log = LoggerFactory.getLogger(javaClass)

    init {
        log.info("Using mock mail service")
    }

    override fun sendMail(mail: MailDto) {
        log.debug("Sending email to ${mail.to} from ${mail.from}:\n${mail.subject}\n${mail.content}")
    }
}
