package com.finpesfatecsp.apikotlin.business.usecase.dto

data class SignUpDTO(
    val id: Long? = null,
    val name: String,
    val surname: String,
    val email: String,
    val password: String,
    val picture: String? = null,
)
