package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.domain.Software
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface SoftwareRepository : JpaRepository<Instance, String> {
    @Query("""
        SELECT s FROM Software s 
        WHERE (:search = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')))
    """)
    fun search(
        @Param("search") search: String,
        pageable: Pageable
    ): Page<Software>
}