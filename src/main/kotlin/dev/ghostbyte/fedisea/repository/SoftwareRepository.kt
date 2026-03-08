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
        SELECT s.identifier, MAX(s.name), MAX(s.website), MAX(s.sourceCode), COUNT(i) as instances, Sum(i.activeUsersHalfyear) as activeUsersHalfyear, sum(i.activeUsersMonth) as activeUsersMonth, sum(i.totalUsers) as totalUsers, sum(i.localPosts) as localPosts, sum(i.localComments) as localComments
        FROM Software s 
        LEFT JOIN Instance i ON s.identifier = i.software 
        AND i.status = 'ACTIVE'
        WHERE (:search = '' OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')))
        GROUP BY s.identifier
    """)
    fun search(
        @Param("search") search: String,
        pageable: Pageable
    ): Page<Array<Any>>
}