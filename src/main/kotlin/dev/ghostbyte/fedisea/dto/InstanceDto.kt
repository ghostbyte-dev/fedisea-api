package dev.ghostbyte.fedisea.dto

import tools.jackson.databind.JsonNode

data class InstanceDto(
    val domain: String,
    val title: String?,
    val description: String?,
    val sourceUrl: String?,
    val thumbnail: String?,
    val software: String?,
    val version: String?,
    val protocols: List<ProtocolDto>,
    val openRegistration: Boolean?,
    val totalUsers: Long?,
    val activeUsersMonth: Long?,
    val activeUsersHalfyear: Long?,
    val localPosts: Long?,
    val localComments: Long?,
    val softwareLogoUrl: String?,
    val metadata: JsonNode?
)