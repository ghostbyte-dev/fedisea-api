package dev.ghostbyte.fedisea.dto

data class SoftwareResponse(
    val identifier: String,
    val name: String?,
    val website: String?,
    val sourceCode: String?,
    val description: String?,
    val licence: String?,
    val joinUrl: String?,
    val instances: Long?,
    val activeUsersHalfyear: Long?,
    val activeUsersMonthly: Long?,
    val totalUsers: Long?,
    val localPosts: Long?,
    val localComments: Long?,
    val iconUrl: String?,
)
