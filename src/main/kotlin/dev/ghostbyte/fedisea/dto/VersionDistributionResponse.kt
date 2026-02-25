package dev.ghostbyte.fedisea.dto

data class VersionDistributionResponse(
    val version: String,
    val count: Long,
    val percentage: Double
)