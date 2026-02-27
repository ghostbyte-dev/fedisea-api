package dev.ghostbyte.fedisea.service

import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * Service for managing and retrieving information about Fediverse instances.
 */
interface InstanceService {

    /**
     * Retrieves a paginated list of active instances, filtered by domain name or software type.
     *
     * @param search A partial domain name to filter by (case-insensitive).
     * @param software The specific software type to filter by (e.g., "mastodon").
     * @param pageable Pagination and sorting information.
     * @return A [Page] of [InstanceResponse] objects.
     */
    fun getAll(search: String, software: String, pageable: Pageable): Page<InstanceResponse>

    /**
     * Fetches detailed information for a specific instance by its domain name.
     *
     * @param domain The unique domain name of the instance (e.g., "mastodon.social").
     * @throws NoSuchElementException If no instance is found with the given domain.
     * @return The [InstanceResponse] containing instance details.
     */
    fun getByDomain(domain: String): InstanceResponse

    /**
     * Calculates global statistics across all tracked planets in the constellation.
     *
     * @return A [StatsResponse] containing total instance and user counts.
     */
    fun getStats(): StatsResponse

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