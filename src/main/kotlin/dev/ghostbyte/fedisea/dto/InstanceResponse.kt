package dev.ghostbyte.fedisea.dto

data class InstanceResponse (
    val domain: String,
    val userCount: Long?,
    val statusCount: Long?,
    val peerCount: Long?,
    val software: String?,
    val version: String?
)