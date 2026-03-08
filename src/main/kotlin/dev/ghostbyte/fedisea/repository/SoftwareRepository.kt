package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import dev.ghostbyte.fedisea.domain.InstanceStatus
import dev.ghostbyte.fedisea.domain.Software
import dev.ghostbyte.fedisea.dto.SoftwareResponse
import dev.ghostbyte.fedisea.repository.projection.SoftwareProjection
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.Optional

interface SoftwareRepository : JpaRepository<Instance, String> {
    @Query(
        """
        SELECT 
            s.identifier as identifier,
            MAX(s.name) as name,
            MAX(s.website) as website,
            MAX(s.sourceCode) as sourceCode,
            MAX(s.description) as description,
            MAX(s.licence) as licence,
            MAX(s.joinUrl) as joinUrl,
            MAX(s.iconName) as iconName,
            COUNT(i) as instances,
            Sum(i.activeUsersHalfyear) as activeUsersHalfyear,
            sum(i.activeUsersMonth) as activeUsersMonth,
            sum(i.totalUsers) as totalUsers,
            sum(i.localPosts) as localPosts,
            sum(i.localComments) as localComments
        FROM Software s 
        LEFT JOIN Instance i ON s.identifier = i.software 
        AND i.status = 'ACTIVE'
        WHERE (:search = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')))
        GROUP BY s.identifier
    """
    )
    fun search(
        @Param("search") search: String,
        pageable: Pageable
    ): Page<SoftwareProjection>?

    @Query(
        """
        SELECT 
            s.identifier as identifier,
            MAX(s.name) as name,
            MAX(s.website) as website,
            MAX(s.sourceCode) as sourceCode,
            MAX(s.description) as description,
            MAX(s.licence) as licence,
            MAX(s.joinUrl) as joinUrl,
            MAX(s.iconName) as iconName,
            COUNT(i) as instances,
            Sum(i.activeUsersHalfyear) as activeUsersHalfyear,
            sum(i.activeUsersMonth) as activeUsersMonth,
            sum(i.totalUsers) as totalUsers,
            sum(i.localPosts) as localPosts,
            sum(i.localComments) as localComments
	    FROM Software s 
	    LEFT JOIN Instance i ON s.identifier = i.software 
		    AND i.status = dev.ghostbyte.fedisea.domain.InstanceStatus.ACTIVE 
	    WHERE s.identifier = :search
	    GROUP BY s.identifier
"""
    )
    fun getByIdentifier(@Param("search") search: String): SoftwareProjection?

    @Modifying
    @Transactional
    @Query("UPDATE Software s SET s.iconName = :iconUrl WHERE s.identifier = :identifier")
    fun updateIconUrl(@Param("identifier") identifier: String, @Param("iconUrl") iconUrl: String): Int
}