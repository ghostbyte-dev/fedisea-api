package dev.ghostbyte.fedisea.service


import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.mapper.toResponse
import dev.ghostbyte.fedisea.repository.InstanceRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InstanceService(
    private val repository: InstanceRepository
) {

    @Transactional(readOnly = true)
    fun getAll(pageable: Pageable): Page<InstanceResponse> {
        return repository.findAll(pageable)
            .map { it.toResponse() }
    }

    @Transactional(readOnly = true)
    fun getByDomain(domain: String): InstanceResponse {
        return repository.findById(domain)
            .map { it.toResponse() }
            .orElseThrow { NoSuchElementException("Instance with domain $domain not found") }
    }

    @Transactional(readOnly = true)
    fun getStats(): StatsResponse {
        val count = repository.count()
        val userCount = repository.sumTotalUsers() ?: 0L

        return StatsResponse(totalInstances = count, totalUsers = userCount)
    }

    @Transactional(readOnly = true)
    fun getSoftwareDistribution(): List<SoftwareDistributionResponse> {
        val rawData = repository.countGroupBySoftware()
        val totalInstances = repository.count().toDouble()

        if (totalInstances == 0.0) return emptyList()

        return rawData.map { row ->
            val name = row[0] as? String ?: "Unknown"
            val count = row[1] as Long
            val percentage = (count / totalInstances) * 100

            SoftwareDistributionResponse(
                software = name,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0 // Round to 2 decimal places
            )
        }.sortedByDescending { it.count } // Sort by most popular first
    }

    @Transactional(readOnly = true)
    fun getVersionDistribution(software: String): List<VersionDistributionResponse> {
        val rawData = repository.countGroupByVersionForSoftware(software)
        val totalForSoftware = repository.countBySoftware(software).toDouble()

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