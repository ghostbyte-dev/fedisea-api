package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.StatsResponse
import dev.ghostbyte.fedisea.service.InstanceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
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
}