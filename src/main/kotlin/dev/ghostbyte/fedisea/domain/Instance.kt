package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "instance")
class Instance(

    @Id
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