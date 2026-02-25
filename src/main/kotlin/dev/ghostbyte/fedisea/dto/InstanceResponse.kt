package dev.ghostbyte.fedisea.dto

data class InstanceResponse(
    val domain: String,
    val software: String?,
    val version: String?,
    val openRegistration: String,
    val totalUsers: Long?,
    val activeUsersMonth: Long?,
    val activeUsersHalfyear: Long?,
    val localPosts: Long?,
    val localComments: Long?
)