package dev.ghostbyte.fedisea.mapper

import dev.ghostbyte.fedisea.domain.Software
import dev.ghostbyte.fedisea.dto.SoftwareResponse

fun Software.toResponse(): SoftwareResponse {
    return SoftwareResponse(
        identifier = identifier,
        name = name,
        sourceCode = sourceCode,
        website = website
    )
}