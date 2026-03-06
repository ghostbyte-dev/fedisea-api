package dev.ghostbyte.fedisea.service

import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.dto.StatsResponse
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
     * @return A [Page] of [InstanceDto] objects.
     */
    fun getAll(search: String, software: String, pageable: Pageable): Page<InstanceDto>

    /**
     * Fetches detailed information for a specific instance by its domain name.
     *
     * @param domain The unique domain name of the instance (e.g., "mastodon.social").
     * @throws dev.ghostbyte.fedisea.exception.ResourceNotFoundException If no instance is found with the given domain.
     * @return The [InstanceDto] containing instance details.
     */
    fun getByDomain(domain: String): InstanceDto

    /**
     * Calculates global statistics across all tracked planets in the constellation.
     *
     * @return A [StatsResponse] containing total instance and user counts.
     */
    fun getStats(): StatsResponse


}