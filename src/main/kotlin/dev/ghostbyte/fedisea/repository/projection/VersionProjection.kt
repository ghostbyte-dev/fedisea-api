package dev.ghostbyte.fedisea.repository.projection

interface VersionProjection {
    val version: String?
    val count: Long
}