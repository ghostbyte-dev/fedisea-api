package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.dto.InstanceResponse

fun Instance.toResponse() = InstanceResponse(
    domain = domain,
    software = software,
    version = software_version,
    openRegistration = open_registration,
    totalUsers = totalUsers,
    activeUsersMonth = activeUsersMonth,
    activeUsersHalfyear = activeUsersHalfyear,
    localPosts = local_posts,
    localComments = local_comments,
    title = title,
    description = description,
    sourceUrl = source_url,
    thumbnail = thumbnail,
)