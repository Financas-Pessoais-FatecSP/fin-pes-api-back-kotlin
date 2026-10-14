package com.finpesfatecsp.apikotlin.rest.request

data class SignInRequest(
    val email: String,
    val password: String,
)
