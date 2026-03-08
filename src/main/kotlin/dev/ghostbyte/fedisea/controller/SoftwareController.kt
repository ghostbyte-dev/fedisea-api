package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.PaginatedResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.service.SoftwareService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/software")
class SoftwareController(
    private val service: SoftwareService
) {

    @GetMapping("/distribution")
    fun getSoftwareDistribution(
        @RequestParam(required = false) limit: Int?
    ): List<SoftwareDistributionResponse> {
        return service.getSoftwareDistribution(limit)
    }

    @GetMapping("/{software}/versions")
    fun getVersionDistribution(
        @PathVariable software: String
    ): List<VersionDistributionResponse> {
        return service.getVersionDistribution(software)
    }

    @GetMapping()
    fun getAllSoftware(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "") search: String,
        ): PaginatedResponse<SoftwareResponse> {
        val pageable = PageRequest.of(page, size)

        val pageResult = service.getAll(search, pageable)
        return PaginatedResponse.fromPage(pageResult)
    }
}