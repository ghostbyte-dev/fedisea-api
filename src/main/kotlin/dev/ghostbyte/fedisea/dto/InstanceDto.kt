package dev.ghostbyte.fedisea.dto

import com.fasterxml.jackson.annotation.JsonRawValue

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
    val country: String?,
    val city: String?,
    val asnName: String?,
    @JsonRawValue
    val metadata: String?
)