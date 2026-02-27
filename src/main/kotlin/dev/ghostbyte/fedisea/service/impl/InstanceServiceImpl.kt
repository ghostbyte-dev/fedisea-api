package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.exception.ResourceNotFoundException
import dev.ghostbyte.fedisea.mapper.toResponse
import dev.ghostbyte.fedisea.repository.InstanceRepository
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InstanceServiceImpl(
    private val repository: InstanceRepository
): InstanceService {

    @Transactional(readOnly = true)
    override fun getAll(search: String, software: String, pageable: Pageable): Page<InstanceResponse> {
        return repository.searchActive(search, software, pageable)
            .map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    override fun getByDomain(domain: String): InstanceResponse {
        return repository.findById(domain)
            .map { it.toResponse() }
            .orElseThrow { ResourceNotFoundException("Server", domain) }
    }

    @Transactional(readOnly = true)
    override fun getStats(): StatsResponse {
        val count = repository.countByStatus(InstanceStatus.ACTIVE)
        val userCount = repository.sumTotalUsers() ?: 0L

        return StatsResponse(totalInstances = count, totalUsers = userCount)
    }

    @Transactional(readOnly = true)
    override fun getSoftwareDistribution(limit: Int?): List<SoftwareDistributionResponse> {
        val rawData = repository.countGroupBySoftware()
        val totalInstances = repository.countByStatus(InstanceStatus.ACTIVE).toDouble()

        if (totalInstances == 0.0) return emptyList()

        val distribution = rawData.map { row ->
            val name = row[0] as? String ?: "Unknown"
            val count = row[1] as Long
            val percentage = (count / totalInstances) * 100

            SoftwareDistributionResponse(
                software = name,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0
            )
        }.sortedByDescending { it.count }

        // Apply the limit if provided, otherwise return the full list
        return if (limit != null) {
            distribution.take(limit)
        } else {
            distribution
        }
    }

    @Transactional(readOnly = true)
    override fun getVersionDistribution(software: String): List<VersionDistributionResponse> {
        val rawData = repository.countGroupByVersionForSoftware(software)
        val totalForSoftware = repository.countBySoftwareAndStatus(software, InstanceStatus.ACTIVE).toDouble()

        if (totalForSoftware == 0.0) return emptyList()

        return rawData.map { row ->
            val version = row[0] as? String ?: "Unknown"
            val count = row[1] as Long
            val percentage = (count / totalForSoftware) * 100

            VersionDistributionResponse(
                version = version,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0
            )
        }.sortedByDescending { it.count }
    }
}