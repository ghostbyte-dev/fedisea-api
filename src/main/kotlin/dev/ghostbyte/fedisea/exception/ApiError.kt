package dev.ghostbyte.fedisea.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ApiError(
    val status: HttpStatus,
    val message: String,
    val errors: List<String> = emptyList(),
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val path: String? = null
)