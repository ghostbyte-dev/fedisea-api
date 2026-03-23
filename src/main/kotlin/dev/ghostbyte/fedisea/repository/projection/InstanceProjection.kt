package dev.ghostbyte.fedisea.repository.projection

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.Protocol
import org.springframework.beans.factory.annotation.Value

interface InstanceProjection {
    val instance: Instance
    val iconName: String?
}