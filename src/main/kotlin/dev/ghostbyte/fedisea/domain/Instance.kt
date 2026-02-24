package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
@Table(name = "instances")
class Instance(

    @Id
    val id: UUID,

    @Column(nullable = false, unique = true)
    val domain: String,

    val userCount: Long?,
    val statusCount: Long?,
    val peerCount: Long?,

    val software: String?,
    val version: String?,

    val lastCrawledAt: Instant?
)