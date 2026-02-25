package dev.ghostbyte.fedisea.service


import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
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
        return StatsResponse(totalInstances = count)
    }
}