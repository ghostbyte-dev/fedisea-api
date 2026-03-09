package dev.ghostbyte.fedisea.dto

data class StatsResponse(
    val totalInstances: Long,
    val totalUsers: Long,
    val totalActiveUsersMonth: Long,
    val totalActiveUsersHalfYear: Long,
    val totalPosts: Long,
    val totalComments: Long,
)