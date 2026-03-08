package dev.ghostbyte.fedisea.dto

data class SoftwareDistributionResponse(
    val software: String,
    val name: String?,
    val count: Long,
    val percentage: Double
)