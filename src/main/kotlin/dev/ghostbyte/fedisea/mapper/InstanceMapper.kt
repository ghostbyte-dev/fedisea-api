package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.dto.InstanceResponse

fun Instance.toResponse() = InstanceResponse(
    domain = domain,
    userCount = userCount,
    statusCount = statusCount,
    peerCount = peerCount,
    software = software,
    version = version
)