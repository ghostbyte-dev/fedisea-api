package dev.ghostbyte.fedisea.repository.projection

interface SoftwareProjection {
    val identifier: String
    val name: String
    val website: String?
    val sourceCode: String?
    val instances: Long?
    val activeUsersHalfyear: Long?
    val activeUsersMonth: Long?
    val totalUsers: Long?
    val localPosts: Long?
    val localComments: Long?
}