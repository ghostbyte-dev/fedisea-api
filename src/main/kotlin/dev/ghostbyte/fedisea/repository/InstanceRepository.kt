package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.repository.projection.GlobalCountsProjection
import dev.ghostbyte.fedisea.repository.projection.VersionProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface InstanceRepository : JpaRepository<Instance, String> {

    fun findFirstByDomain(domain: String): Instance?

    @Query("""
        SELECT DISTINCT i as instance FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        AND i.software.identifier != 'gotosocial' 
        AND (:search = '' OR LOWER(i.domain) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:software = '' OR i.software.identifier = :software)
    """)
    fun searchActive(
        @Param("search") search: String,
        @Param("software") software: String,
        pageable: Pageable
    ): Page<Instance>

    @Query("""
        SELECT 
            COUNT(i) as totalInstances,
            SUM(i.totalUsers) as totalUsers,
            SUM(i.activeUsersMonth) as totalActiveUsersMonth,
            SUM(i.activeUsersHalfyear) as totalActiveUsersHalfYear,
            SUM(i.localPosts) as totalPosts,
            SUM(i.localComments) as totalComments
        FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE
        AND i.software.identifier != 'gotosocial'
    """)
    fun getGlobalCounts(): GlobalCountsProjection

    @Query(
        """
        SELECT i.softwareVersion as version, COUNT(i) as count 
        FROM Instance i 
        WHERE i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
        AND i.software.identifier = :software
        GROUP BY i.softwareVersion
        ORDER BY COUNT(i) DESC
    """
    )
    fun countGroupByVersionForSoftware(@Param("software") software: String, pageable: Pageable): Page<VersionProjection>

    @Query(
        """
            SELECT COUNT(i)
            FROM Instance i
            WHERE i.status = :status
            AND i.software.identifier = :software
        """
    )
    fun countBySoftwareAndStatus(@Param("software") software: String, @Param("status") status: InstanceStatus): Long
}