package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.InstanceStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface InstanceRepository : JpaRepository<Instance, String> {

    fun findAllByStatus(status: InstanceStatus, pageable: Pageable): Page<Instance>

    @Query("""
        SELECT i FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        AND i.software != 'gotosocial' 
        AND (:search = '' OR LOWER(i.domain) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:software = '' OR i.software = :software)
    """)
    fun searchActive(
        @Param("search") search: String,
        @Param("software") software: String,
        pageable: Pageable
    ): Page<Instance>

    @Query("SELECT SUM(i.totalUsers) FROM Instance i WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE")
    fun sumTotalUsers(): Long?

    @Query("""
        SELECT i.software, COUNT(i) 
        FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        GROUP BY i.software
    """)
    fun countGroupBySoftware(): List<Array<Any>>

    @Query("""
        SELECT i.software_version, COUNT(i) 
        FROM Instance i 
        WHERE i.software = :software 
        AND i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        GROUP BY i.software_version
    """)
    fun countGroupByVersionForSoftware(software: String): List<Array<Any>>

    fun countBySoftwareAndStatus(software: String, status: InstanceStatus): Long

    fun countByStatus(status: InstanceStatus): Long
}