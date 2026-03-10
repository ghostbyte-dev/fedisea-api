package dev.ghostbyte.fedisea.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import java.time.OffsetDateTime
import java.time.OffsetTime

@Entity
@Table(name = "instance")
class Instance(
    @Id
    @Column(nullable = false, unique = true)
    val domain: String,
    val title: String?,
    @Column(columnDefinition = "TEXT")
    val description: String?,
    @Column(name = "source_url")
    val sourceUrl: String?,
    val thumbnail: String?,
    val software: String?,
    val email: String?,
    @Column(name = "software_version")
    val softwareVersion: String?,
    @Column(name = "open_registration")
    val openRegistration: Boolean?,
    @Column(name = "total_users")
    val totalUsers: Long?,
    @Column(name = "active_users_month")
    val activeUsersMonth: Long?,
    @Column(name = "active_users_halfyear")
    val activeUsersHalfyear: Long?,
    @Column(name = "local_posts")
    val localPosts: Long?,
    @Column(name = "local_comments")
    val localComments: Long?,
    @Enumerated(EnumType.STRING)
    val status: InstanceStatus?,
    @Column(name = "points_to")
    val pointsTo: String?,
    @Column(name = "last_seen", columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    @NotNull
    val lastSeen: OffsetDateTime
)