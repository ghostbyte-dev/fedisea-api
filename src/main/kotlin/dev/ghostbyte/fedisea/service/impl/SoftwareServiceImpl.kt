package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.exception.ResourceNotFoundException
import dev.ghostbyte.fedisea.repository.InstanceRepository
import dev.ghostbyte.fedisea.repository.SoftwareRepository
import dev.ghostbyte.fedisea.service.SoftwareService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SoftwareServiceImpl(
    private val repository: InstanceRepository,
    private val softwareRepository: SoftwareRepository
) : SoftwareService {
    override fun getAll(search: String, pageable: Pageable): Page<SoftwareResponse> {
        val result =  softwareRepository.search(search, pageable) ?: throw ResourceNotFoundException("Software", search);

        return result.map {
            SoftwareResponse(
                identifier = it.identifier,
                name = it.name,
                website = it.website,
                sourceCode = it.sourceCode,
                instances = it.instances,
                activeUsersMonthly = it.activeUsersMonth,
                activeUsersHalfyear = it.activeUsersHalfyear,
                totalUsers = it.totalUsers,
                localPosts = it.localPosts,
                localComments = it.localComments
            )
        }
    }

    override fun getByIdentifier(identifier: String): SoftwareResponse {
        return softwareRepository.getByIdentifier(identifier)
            ?: throw ResourceNotFoundException("Software", identifier)
    }

    @Transactional(readOnly = true)
    override fun getSoftwareDistribution(limit: Int?): List<SoftwareDistributionResponse> {
        val rawData = repository.countGroupBySoftware()
        val totalInstances = repository.countByStatus(InstanceStatus.ACTIVE).toDouble()

        if (totalInstances == 0.0) return emptyList()

        val distribution = rawData.map { row ->
            val software = row[0] as? String ?: "Unknown"
            val softwareName = row[1] as String?
            val count = row[2] as Long
            val percentage = (count / totalInstances) * 100

            SoftwareDistributionResponse(
                software = software,
                name = softwareName,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0
            )
        }

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