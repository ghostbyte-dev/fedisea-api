package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
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
            .orElseThrow { NoSuchElementException("Instance with domain $domain not found") }
    }

    @Transactional(readOnly = true)
    override fun getStats(): StatsResponse {
        val count = repository.countByStatus(InstanceStatus.ACTIVE)
        val userCount = repository.sumTotalUsers() ?: 0L

        return StatsResponse(totalInstances = count, totalUsers = userCount)
    }


}