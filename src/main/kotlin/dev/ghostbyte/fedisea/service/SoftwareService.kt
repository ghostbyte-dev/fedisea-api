package dev.ghostbyte.fedisea.service

import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse

/**
 * Service for managing and retrieving information about Fediverse software.
 */
interface SoftwareService {

    /**
     * Analyzes the distribution of different software types across the network.
     *
     * @param limit The maximum number of software types to return, sorted by popularity.
     * @return A list of [SoftwareDistributionResponse] items.
     */
    fun getSoftwareDistribution(limit: Int?): List<SoftwareDistributionResponse>

    /**
     * Breaks down the versions used by a specific software type.
     *
     * @param software The software name to analyze (e.g., "lemmy").
     * @return A list of [VersionDistributionResponse] items showing version popularity.
     */
    fun getVersionDistribution(software: String): List<VersionDistributionResponse>
}