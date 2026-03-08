package dev.ghostbyte.fedisea.service.impl

import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.dto.SoftwareDistributionResponse
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.dto.VersionDistributionResponse
import dev.ghostbyte.fedisea.exception.ResourceNotFoundException
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
            SoftwareResponse(
                identifier = it.identifier,
                name = it.name,
                website = it.website,
                sourceCode = it.sourceCode,
                instances = it.instances,
                activeUsersMonthly = it.activeUsersMonth,
                activeUsersHalfyear = it.activeUsersHalfyear,
                totalUsers = it.totalUsers,
                localPosts = it.localPosts,
                localComments = it.localComments,
                joinUrl = it.joinUrl,
                licence = it.licence,
                description = it.description,
                iconUrl = "https://assets.fedisea.surf/" + it.iconName
            )
        }
    }

    override fun getByIdentifier(identifier: String): SoftwareResponse {
        val projection = softwareRepository.getByIdentifier(identifier)
            ?: throw ResourceNotFoundException("Software", identifier)

        return SoftwareResponse(
            identifier = projection.identifier,
            name = projection.name,
            website = projection.website,
            sourceCode = projection.sourceCode,
            instances = projection.instances,
            activeUsersMonthly = projection.activeUsersMonth,
            activeUsersHalfyear = projection.activeUsersHalfyear,
            totalUsers = projection.totalUsers,
            localPosts = projection.localPosts,
            localComments = projection.localComments,
            joinUrl = projection.joinUrl,
            licence = projection.licence,
            description = projection.description,
            iconUrl = "https://assets.fedisea.surf/" + projection.iconName
        )
    }

    @Transactional(readOnly = true)
    override fun getSoftwareDistribution(limit: Int?): List<SoftwareDistributionResponse> {
        val rawData = repository.countGroupBySoftware()
        val totalInstances = repository.countByStatus(InstanceStatus.ACTIVE).toDouble()

        if (totalInstances == 0.0) return emptyList()

        val distribution = rawData.map { row ->
            val software = row[0] as? String ?: "Unknown"
            val softwareName = row[1] as String?
            val count = row[2] as Long
            val percentage = (count / totalInstances) * 100

            SoftwareDistributionResponse(
                software = software,
                name = softwareName,
                count = count,
                percentage = Math.round(percentage * 100.0) / 100.0
            )
        }

        // Apply the limit if provided, otherwise return the full list
        return if (limit != null) {
            distribution.take(limit)
        } else {
            distribution
        }
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
        val iconPath = "/app/icons"
        val baseUrl = "https://assets.fedisea.surf"
        if (file.isEmpty) throw Exception("empty file")

        val rootPath = Paths.get(iconPath)
        if (!Files.exists(rootPath)) Files.createDirectories(rootPath)

        val extension = file.originalFilename?.substringAfterLast(".", "png") ?: "png"
        val fileName = "$identifier.$extension"
        val destinationPath = rootPath.resolve(fileName)

        file.inputStream.use { input ->
            copy(input, destinationPath, StandardCopyOption.REPLACE_EXISTING)
        }

        softwareRepository.updateIconUrl(identifier, fileName)
        val fileUrl = "$baseUrl/$fileName"
        return fileUrl
    }
}