package fr.agaspardcilia.boilerplate.users

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByMail(mail: String): User?

    fun existsByMail(mail: String): Boolean
}
