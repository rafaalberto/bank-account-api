package br.com.bankaccount.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(AppException::class)
    fun handleAppException(exception: AppException) =
            ResponseEntity.badRequest().body(ErrorResponse(exception.description, exception.httpStatus))

}

class ErrorResponse(val message: String, val httpStatus: HttpStatus)