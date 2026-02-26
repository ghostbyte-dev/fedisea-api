package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/stats")
class StatsController(
    private val service: InstanceService
) {

    @GetMapping
    fun getStats(): StatsResponse {
        return service.getStats()
    }

    @GetMapping("/software")
    fun getSoftwareDistribution(
        @RequestParam(required = false) limit: Int?
    ): List<SoftwareDistributionResponse> {
        return service.getSoftwareDistribution(limit)
    }

    @GetMapping("/software/{software}/versions")
    fun getVersionDistribution(
        @PathVariable software: String
    ): List<VersionDistributionResponse> {
        return service.getVersionDistribution(software)
    }
}