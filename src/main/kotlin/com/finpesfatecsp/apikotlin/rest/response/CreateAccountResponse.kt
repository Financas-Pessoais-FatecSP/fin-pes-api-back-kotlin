package com.finpesfatecsp.apikotlin.rest.response

data class CreateAccountResponse(
    val accounts: List<AccountResponse>,
)

data class AccountResponse(
    val id: Long,
    val name: String,
)
