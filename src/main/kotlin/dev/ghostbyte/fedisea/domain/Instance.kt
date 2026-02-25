package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "instances")
class Instance(

    @Id
    val id: UUID,

    @Column(nullable = false, unique = true)
    val domain: String,

    val software: String?,
    val software_version: String?,

    val open_registration: String,

    val total_users: Long?,
    val active_users_month: Long?,
    val active_users_halfyear: Long?,
    val local_posts: Long?,
    val local_comments: Long?,
)