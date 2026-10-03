package com.finpesfatecsp.apikotlin.rest.controller.request

class SignUpRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val picture: String?,
)
