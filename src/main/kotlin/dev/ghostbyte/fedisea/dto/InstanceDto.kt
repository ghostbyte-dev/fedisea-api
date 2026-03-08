package dev.ghostbyte.fedisea.dto

data class InstanceDto(
    val domain: String,
    val title: String?,
    val description: String?,
    val sourceUrl: String?,
    val thumbnail: String?,
    val software: String?,
    val version: String?,
    val openRegistration: Boolean?,
    val totalUsers: Long?,
    val activeUsersMonth: Long?,
    val activeUsersHalfyear: Long?,
    val localPosts: Long?,
    val localComments: Long?,
    val softwareLogoUrl: String?
)