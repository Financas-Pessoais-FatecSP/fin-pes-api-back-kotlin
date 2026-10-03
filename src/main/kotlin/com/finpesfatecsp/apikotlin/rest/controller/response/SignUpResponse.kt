package com.finpesfatecsp.apikotlin.rest.controller.response

class SignUpResponse(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val picture: String?,
)
