package dev.ghostbyte.fedisea.repository

import dev.ghostbyte.fedisea.domain.Instance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface InstanceRepository : JpaRepository<Instance, String> {

    @Query("SELECT SUM(i.total_users) FROM Instance i")
    fun sumTotalUsers(): Long?

    @Query("SELECT i.software, COUNT(i) FROM Instance i GROUP BY i.software")
    fun countGroupBySoftware(): List<Array<Any>>

    @Query("""
        SELECT i.software_version, COUNT(i) 
        FROM Instance i 
        WHERE i.software = :software 
        GROUP BY i.software_version
    """)
    fun countGroupByVersionForSoftware(software: String): List<Array<Any>>

    fun countBySoftware(software: String): Long
}