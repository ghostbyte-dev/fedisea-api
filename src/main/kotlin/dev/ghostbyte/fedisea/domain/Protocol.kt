package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*

@Entity
@Table(name = "protocol")
class Protocol(
    @Id
    val identifier: String,

    @Column(unique = true)
    val name: String? = null,

    @Column(length = 1000)
    val description: String? = null,

    val homepage: String? = null,
)