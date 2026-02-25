package dev.ghostbyte.fedisea.dto

data class SoftwareDistributionResponse(
    val software: String,
    val count: Long,
    val percentage: Double
)