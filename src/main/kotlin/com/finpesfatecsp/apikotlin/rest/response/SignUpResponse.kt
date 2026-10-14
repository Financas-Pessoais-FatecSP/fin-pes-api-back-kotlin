package com.finpesfatecsp.apikotlin.rest.response

data class SignUpResponse(
    val name: String,
    val surname: String,
    val picture: String?,
    val token: String,
)
