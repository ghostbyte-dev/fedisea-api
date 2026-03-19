package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.exception.ResourceNotFoundException
import dev.ghostbyte.fedisea.mapper.toResponse
import dev.ghostbyte.fedisea.repository.InstanceRepository
import dev.ghostbyte.fedisea.repository.SoftwareRepository
import dev.ghostbyte.fedisea.service.SoftwareService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Files.copy
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class SoftwareServiceImpl(
    private val repository: InstanceRepository,
    private val softwareRepository: SoftwareRepository
) : SoftwareService {
    override fun getAll(search: String, pageable: Pageable): Page<SoftwareResponse> {
        val result =  softwareRepository.search(search, pageable) ?: throw ResourceNotFoundException("Software", search);

        return result.map {
            it.toResponse()
        }
    }

    override fun getByIdentifier(identifier: String): SoftwareResponse {
        val projection = softwareRepository.getByIdentifier(identifier)
            ?: throw ResourceNotFoundException("Software", identifier)

        return projection.toResponse()
    }

    @Transactional(readOnly = true)
    override fun getVersionDistribution(software: String, pageable: Pageable): Page<VersionDistributionResponse> {
        val versionProjections = repository.countGroupByVersionForSoftware(software, pageable)
        val totalForSoftware = repository.countBySoftwareAndStatus(software, InstanceStatus.ACTIVE).toDouble()

        if (totalForSoftware == 0.0) return Page.empty()

        return versionProjections.map { versionProjection ->
            val version = versionProjection.version ?: "Unknown"
            val count = versionProjection.count
            val percentage = (count / totalForSoftware) * 100

            VersionDistributionResponse(
                version = version,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0
            )
        }
    }

    override fun uploadFile(identifier: String, file: MultipartFile): String {
        val iconPath = "/app/icons/logos"
        val baseUrl = "https://assets.fedisea.surf/logos"
        if (file.isEmpty) throw Exception("empty file")

        val rootPath = Paths.get(iconPath)
        if (!Files.exists(rootPath)) Files.createDirectories(rootPath)

        val extension = file.originalFilename?.substringAfterLast(".", "png") ?: "png"
        val fileName = "${identifier.trim()}.${extension.trim()}"
        val destinationPath = rootPath.resolve(fileName)

        file.inputStream.use { input ->
            copy(input, destinationPath, StandardCopyOption.REPLACE_EXISTING)
        }

        softwareRepository.updateIconUrl(identifier, fileName)
        val fileUrl = "$baseUrl/$fileName"
        return fileUrl
    }
}