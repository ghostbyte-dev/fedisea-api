package dev.ghostbyte.fedisea.service

import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service for managing and retrieving information about Fediverse software.
 */
interface SoftwareService {

    /**
     * Get all software
     *
     * @param search A partial domain name to filter by (case-insensitive).
     * @param pageable Pagination and sorting information.
     *
     * @return A [Page] of [SoftwareResponse] objects.
     *
     */
    fun getAll(search: String, pageable: Pageable): Page<SoftwareResponse>

    /**
     *
     * @param identifier the identifier of the software
     *
     * @return A [SoftwareResponse] object
     */
    fun getByIdentifier(identifier: String): SoftwareResponse

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