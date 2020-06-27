package br.com.bankaccount.exception

import org.springframework.http.HttpStatus

class AppException(val description: String, val httpStatus: HttpStatus) : RuntimeException()