package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.repository.projection.SoftwareProjection

fun SoftwareProjection.toResponse() = SoftwareResponse(
    identifier = this.identifier,
    name = this.name,
    website = this.website,
    sourceCode = this.sourceCode,
    instances = this.instances,
    activeUsersMonth = this.activeUsersMonth,
    activeUsersHalfyear = this.activeUsersHalfyear,
    totalUsers = this.totalUsers,
    localPosts = this.localPosts,
    localComments = this.localComments,
    joinUrl = this.joinUrl,
    licence = this.licence,
    description = this.description,
    iconUrl = if (this.iconName != null) {"https://assets.fedisea.surf/logos/" + this.iconName} else {null}
)