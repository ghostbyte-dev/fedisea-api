package dev.ghostbyte.fedisea.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ApiError> {
        val error = ApiError(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = "An unexpected error occurred",
            errors = listOf(exception.localizedMessage),
            path = (request as ServletWebRequest).request.requestURI
        )

        // You might want to log the error here
        // For logging best practices, see: https://kotlincraft.dev/articles/kotlin-logging-a-complete-guide-to-better-logging-in-kotlin

        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(
        exception: MethodArgumentNotValidException,
        request: WebRequest
    ): ResponseEntity<ApiError> {
        val errors = exception.bindingResult
            .fieldErrors
            .map { "${it.field}: ${it.defaultMessage}" }

        val error = ApiError(
            status = HttpStatus.BAD_REQUEST,
            message = "Validation failed",
            errors = errors,
            path = (request as ServletWebRequest).request.requestURI
        )

        return ResponseEntity(error, HttpStatus.BAD_REQUEST)
    }


}