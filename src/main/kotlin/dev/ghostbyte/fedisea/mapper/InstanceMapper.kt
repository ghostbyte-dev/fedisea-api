package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.dto.InstanceDto

fun Instance.toDto() = InstanceDto(
    domain = domain,
    software = software,
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
)