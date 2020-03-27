package fr.agaspardcilia.boilerplate.users

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.sql.Timestamp
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "app_user")
data class User(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    var id: UUID? = null,

    @Column(nullable = false)
    @field:Size(min = 5, max = 254)
    var mail: String? = null,

    @Column(nullable = false)
    var password: String? = null,

    @field:Size(min = 1, max = 50)
    var firstname: String? = null,

    @field:Size(min = 1, max = 50)
    var lastname: String? = null,

    @ElementCollection(targetClass = Authority::class, fetch = FetchType.EAGER)
    @JsonIgnore
    @CollectionTable(name = "user_authority", joinColumns = [JoinColumn(name = "user_id")])
    @Enumerated(EnumType.STRING)
    var authorities: MutableSet<Authority> = mutableSetOf(),

    var isActive: Boolean = false,

    @field:CreationTimestamp
    var creationDate: Timestamp? = null,

    @field:UpdateTimestamp
    var updateDate: Timestamp? = null
)
