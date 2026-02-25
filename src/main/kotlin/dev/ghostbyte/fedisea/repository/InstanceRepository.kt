package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InstanceRepository : JpaRepository<Instance, String>