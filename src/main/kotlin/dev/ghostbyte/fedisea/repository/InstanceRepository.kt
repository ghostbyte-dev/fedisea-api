package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.repository.projection.InstanceProjection
import dev.ghostbyte.fedisea.repository.projection.VersionProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface InstanceRepository : JpaRepository<Instance, String> {
    @Query("""
        SELECT i as instance, s.iconName as iconName FROM Instance i
        JOIN Software s on s.identifier = i.software
        WHERE i.domain = :domain
    """)
    fun findByIdWithSoftwareIcon(@Param("domain") domain: String): InstanceProjection?

    fun findAllByStatus(status: InstanceStatus, pageable: Pageable): Page<Instance>

    @Query("""
        SELECT i as instance, s.iconName as iconName FROM Instance i 
        JOIN Software s on s.identifier = i.software
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        AND i.software != 'gotosocial' 
        AND (:search = '' OR LOWER(i.domain) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:software = '' OR i.software = :software)
    """)
    fun searchActive(
        @Param("search") search: String,
        @Param("software") software: String,
        pageable: Pageable
    ): Page<InstanceProjection>

    @Query("SELECT SUM(i.totalUsers) FROM Instance i WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE")
    fun sumTotalUsers(): Long?

    @Query("""
        SELECT i.software, MAX(s.name), COUNT(i), MAX(s.iconName)
        FROM Instance i
        LEFT JOIN Software s ON i.software = s.identifier
        WHERE i.status = 'ACTIVE' 
        GROUP BY i.software
        ORDER BY COUNT(i) DESC
    """)
    fun countGroupBySoftware(): List<Array<Any>>

    @Query(
        """
        SELECT i.softwareVersion as version, COUNT(i) as count 
        FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        AND i.software = :software
        GROUP BY i.softwareVersion
        ORDER BY COUNT(i) DESC
    """
    )
    fun countGroupByVersionForSoftware(@Param("software") software: String, pageable: Pageable): Page<VersionProjection>

    fun countBySoftwareAndStatus(software: String, status: InstanceStatus): Long

    fun countByStatus(status: InstanceStatus): Long
}