package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.dto.InstanceResponse

fun Instance.toResponse() = InstanceResponse(
    domain = domain,
    software = software,
    version = software_version,
    openRegistration = open_registration,
    totalUsers = total_users,
    activeUsersMonth = active_users_month,
    activeUsersHalfyear = active_users_halfyear,
    localPosts = local_posts,
    localComments = local_comments
)