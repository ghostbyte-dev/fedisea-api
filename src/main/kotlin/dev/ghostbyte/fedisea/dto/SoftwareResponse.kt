package dev.ghostbyte.fedisea.dto

data class SoftwareResponse(
    val identifier: String,
    val name: String,
    val website: String?,
    val sourceCode: String?
)
