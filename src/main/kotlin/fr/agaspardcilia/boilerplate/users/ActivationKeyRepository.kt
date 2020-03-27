package fr.agaspardcilia.boilerplate.users

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ActivationKeyRepository : JpaRepository<ActivationKey, UUID>
