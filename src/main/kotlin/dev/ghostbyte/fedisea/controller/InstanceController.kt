package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.dto.InstanceSort
import dev.ghostbyte.fedisea.dto.PaginatedResponse
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.data.domain.PageRequest
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
        @RequestParam(defaultValue = "users") sort: String,
        @RequestParam(defaultValue = "desc") order: String,
        @RequestParam(defaultValue = "") search: String,
        @RequestParam(defaultValue = "") software: String
    ): PaginatedResponse<InstanceDto> {

        val sortType = InstanceSort.fromString(sort)

        val sortOrder = if (order.equals("desc", ignoreCase = true)) {
            Sort.by(sortType.databaseField).descending()
        } else {
            Sort.by(sortType.databaseField).ascending()
        }

        val pageable = PageRequest.of(page, size, sortOrder)

        val pageResult = service.getAll(search, software, pageable)
        return PaginatedResponse.fromPage(pageResult)
    }

    @GetMapping("/{domain}")
    fun getInstanceByDomain(
        @PathVariable domain: String
    ): InstanceDto {
        return service.getByDomain(domain)
    }
}