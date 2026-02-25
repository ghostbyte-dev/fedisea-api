package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instances")
class InstanceController(
    private val service: InstanceService
) {

    @GetMapping
    fun getInstances(
        pageable: Pageable
    ): Page<InstanceResponse> {
        return service.getAll(pageable)
    }

    @GetMapping("/{domain}")
    fun getInstanceByDomain(
        @PathVariable domain: String
    ): InstanceResponse {
        return service.getByDomain(domain)
    }
}