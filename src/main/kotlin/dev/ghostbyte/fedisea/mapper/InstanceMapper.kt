package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.repository.projection.InstanceProjection

fun Instance.toDto() = InstanceDto(
    domain = domain,
    software = software.identifier,
    version = softwareVersion,
    openRegistration = openRegistration,
    totalUsers = totalUsers,
    activeUsersMonth = activeUsersMonth,
    activeUsersHalfyear = activeUsersHalfyear,
    localPosts = localPosts,
    localComments = localComments,
    title = title,
    description = description,
    sourceUrl = sourceUrl,
    thumbnail = thumbnail,
    softwareLogoUrl = null
)

fun InstanceProjection.toDto() = InstanceDto(
    domain = instance.domain,
    software = instance.software.identifier,
    version = instance.softwareVersion,
    openRegistration = instance.openRegistration,
    totalUsers = instance.totalUsers,
    activeUsersMonth = instance.activeUsersMonth,
    activeUsersHalfyear = instance.activeUsersHalfyear,
    localPosts = instance.localPosts,
    localComments = instance.localComments,
    title = instance.title,
    description = instance.description,
    sourceUrl = instance.sourceUrl,
    thumbnail = instance.thumbnail,
    softwareLogoUrl = if (iconName != null) {
        "https://assets.fedisea.surf/logos/$iconName"
    } else {null},
)