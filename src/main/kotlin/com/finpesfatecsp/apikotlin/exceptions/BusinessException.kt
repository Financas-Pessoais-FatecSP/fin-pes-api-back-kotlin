package com.finpesfatecsp.apikotlin.exceptions

class BusinessException(
    val code: String?,
    message: String,
) : RuntimeException(message)
