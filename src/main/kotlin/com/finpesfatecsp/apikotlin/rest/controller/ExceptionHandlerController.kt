package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ErrorResponse =
        when (exception.code) {
            "user.already.exists" ->
                ErrorResponse.create(
                    exception,
                    HttpStatus.BAD_REQUEST,
                    exception.message ?: "User already exists",
                )

            "signin.failed" ->
                ErrorResponse.create(
                    exception,
                    HttpStatus.UNAUTHORIZED,
                    exception.message ?: "E-mail or password incorrect",
                )

            "account.already.exists" ->
                ErrorResponse.create(
                    exception,
                    HttpStatus.BAD_REQUEST,
                    exception.message ?: "User already have an account with the specified name",
                )

            else ->
                ErrorResponse.create(
                    exception,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    exception.message ?: "Business exception occurred",
                )
        }
}
