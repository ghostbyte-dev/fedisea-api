package dev.ghostbyte.fedisea.controller

import dev.ghostbyte.fedisea.dto.InstanceSort
import dev.ghostbyte.fedisea.dto.PaginatedResponse
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.SoftwareSort
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.service.SoftwareService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

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
    fun getVersions(
        @PathVariable software: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
    ): PaginatedResponse<VersionDistributionResponse> {
        val pageable = PageRequest.of(page, size)
        return PaginatedResponse.fromPage(service.getVersionDistribution(software, pageable))
    }

    @GetMapping()
    fun getAllSoftware(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "activeUsersHalfyear") sort: String,
        @RequestParam(defaultValue = "desc") order: String,
        @RequestParam(defaultValue = "") search: String,
        ): PaginatedResponse<SoftwareResponse> {
        val sortType = SoftwareSort.fromString(sort)

        val sortOrder = if (order.equals("desc", ignoreCase = true)) {
            Sort.by(sortType.databaseField).descending()
        } else {
            Sort.by(sortType.databaseField).ascending()
        }

        val pageable = PageRequest.of(page, size, sortOrder)

        val pageResult = service.getAll(search, pageable)
        return PaginatedResponse.fromPage(pageResult)
    }

    @GetMapping("/{software}")
    fun getSoftware(@PathVariable software: String): SoftwareResponse {
        return service.getByIdentifier(software)
    }

    @PostMapping("/{identifier}/icon", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadIcon(
        @PathVariable identifier: String,
        @RequestParam("file") file: MultipartFile
    ): String {
        return service.uploadFile(identifier, file)
    }
}