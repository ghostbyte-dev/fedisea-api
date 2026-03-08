package dev.ghostbyte.fedisea.repository.projection

import dev.ghostbyte.fedisea.domain.Instance

interface InstanceProjection {
    val instance: Instance
    val iconName: String?
}