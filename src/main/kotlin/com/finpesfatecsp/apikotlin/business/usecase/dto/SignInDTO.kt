package com.finpesfatecsp.apikotlin.business.usecase.dto

data class SignInDTO(
    val id: Long,
    val name: String,
    val surname: String,
    val email: String,
    val picture: String? = null,
)
