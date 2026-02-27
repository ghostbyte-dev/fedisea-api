package dev.ghostbyte.fedisea.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class BusinessExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(
        exception: ResourceNotFoundException,
        request: WebRequest
    ): ResponseEntity<ApiError> {
        val error = ApiError(
            status = HttpStatus.NOT_FOUND,
            message = exception.message ?: "Resource not found",
            path = (request as ServletWebRequest).request.requestURI
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun handleDuplicateResource(
        exception: DuplicateResourceException,
        request: WebRequest
    ): ResponseEntity<ApiError> {
        val error = ApiError(
            status = HttpStatus.CONFLICT,
            message = exception.message ?: "Resource already exists",
            path = (request as ServletWebRequest).request.requestURI
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }
}