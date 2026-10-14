package com.finpesfatecsp.apikotlin.rest.request

data class SignUpRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val picture: String?,
)
