package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "instance")
class Instance(

    @Id
    @Column(nullable = false, unique = true)
    val domain: String,

    val title: String?,
    val description: String?,
    val source_url: String?,
    val thumbnail: String?,


    val software: String?,
    val software_version: String?,

    val open_registration: String,

    @Column(name = "total_users")
    val totalUsers: Long?,
    @Column(name = "active_users_month")
    val activeUsersMonth: Long?,
    @Column(name = "active_users_halfyear")
    val activeUsersHalfyear: Long?,
    val local_posts: Long?,
    val local_comments: Long?,

    @Enumerated(EnumType.STRING)
    val status: InstanceStatus?,
)