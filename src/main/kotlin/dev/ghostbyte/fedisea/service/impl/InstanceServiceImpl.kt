package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.exception.ResourceNotFoundException
import dev.ghostbyte.fedisea.mapper.toDto
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
    override fun getAll(search: String, software: String, pageable: Pageable): Page<InstanceDto> {
        return repository.searchActive(search, software, pageable)
            .map { it.toDto() }
    }

    @Transactional(readOnly = true)
    override fun getByDomain(domain: String): InstanceDto {
        val result = repository.findByIdWithSoftwareIcon(domain) ?: throw ResourceNotFoundException("Instance", domain)
        return result.toDto()
    }

    @Transactional(readOnly = true)
    override fun getStats(): StatsResponse {
        val globalCounts = repository.getGlobalCounts()

        return StatsResponse(
            totalInstances = globalCounts.totalInstances,
            totalUsers = globalCounts.totalUsers,
            totalActiveUsersMonth = globalCounts.totalActiveUsersMonth,
            totalActiveUsersHalfYear = globalCounts.totalActiveUsersHalfYear,
            totalPosts = globalCounts.totalPosts,
            totalComments = globalCounts.totalComments
        )
    }


}