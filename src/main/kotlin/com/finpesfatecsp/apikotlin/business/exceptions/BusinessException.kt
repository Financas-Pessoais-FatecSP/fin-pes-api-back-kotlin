package com.finpesfatecsp.apikotlin.business.exceptions

class BusinessException(
    val code: String?,
    message: String,
) : RuntimeException(message)
