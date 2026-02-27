package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.InstanceResponse
import dev.ghostbyte.fedisea.dto.PaginatedResponse
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/instances")
class InstanceController(
    private val service: InstanceService
) {

    @GetMapping
    fun getInstances(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "activeUsersMonth") sortBy: String,
        @RequestParam(defaultValue = "desc") direction: String,
        @RequestParam(defaultValue = "") search: String,
        @RequestParam(defaultValue = "") software: String
    ): PaginatedResponse<InstanceResponse> {
        val sort = if (direction.equals("desc", ignoreCase = true)) {
            Sort.by(sortBy).descending()
        } else {
            Sort.by(sortBy).ascending()
        }

        val pageable = PageRequest.of(page, size, sort)

        val pageResult = service.getAll(search, software, pageable)
        return PaginatedResponse.fromPage(pageResult)
    }

    @GetMapping("/{domain}")
    fun getInstanceByDomain(
        @PathVariable domain: String
    ): InstanceResponse {
        return service.getByDomain(domain)
    }
}