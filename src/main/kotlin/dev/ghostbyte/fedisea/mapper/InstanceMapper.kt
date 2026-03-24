package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.Protocol
import dev.ghostbyte.fedisea.dto.InstanceDto
import dev.ghostbyte.fedisea.dto.ProtocolDto
import dev.ghostbyte.fedisea.repository.projection.InstanceProjection

fun Protocol.toDto() = ProtocolDto(
    identifier = identifier,
    name = name,
    description = description,
    website = homepage
)

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
    softwareLogoUrl = software.iconName?.let { "https://assets.fedisea.surf/logos/$it" },
    protocols = protocols.map { it.toDto() },
    country = country,
    city = city,
    asnName = asnName,
    metadata = metadata?.toString(),
    lastSeen = lastSeen
)