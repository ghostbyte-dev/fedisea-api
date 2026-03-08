package dev.ghostbyte.fedisea.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "software")
class Software(
    @Id
    val identifier: String,
    val name: String,
    val website: String? = null,
    @Column(name = "soure_code")
    val sourceCode: String? = null,
    val description: String? = null,
    val licence: String? = null,
    @Column(name = "join_url")
    val joinUrl: String? = null,
    @Column(name = "icon_name")
    val iconName: String? = null,
)