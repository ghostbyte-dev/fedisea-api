package dev.ghostbyte.fedisea.dto

import org.springframework.data.domain.Page

data class PaginatedResponse<T>(
    val data: List<T>,
    val currentPage: Int,
    val totalPages: Int,
    val totalItems: Long,
    val pageSize: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
) {
    companion object {
        /**
         * Maps a Spring Data Page to our custom PaginatedResponse
         */
        fun <T : Any> fromPage(page: Page<T>): PaginatedResponse<T> {
            return PaginatedResponse(
                data = page.content,
                currentPage = page.number,
                totalPages = page.totalPages,
                totalItems = page.totalElements,
                pageSize = page.size,
                hasNext = page.hasNext(),
                hasPrevious = page.hasPrevious()
            )
        }
    }
}