package dev.ghostbyte.fedisea.repository.projection

interface GlobalCountsProjection {
    val totalInstances: Long
    val totalUsers: Long
    val totalActiveUsersMonth: Long
    val totalActiveUsersHalfYear: Long
    val totalPosts: Long
    val totalComments: Long
}