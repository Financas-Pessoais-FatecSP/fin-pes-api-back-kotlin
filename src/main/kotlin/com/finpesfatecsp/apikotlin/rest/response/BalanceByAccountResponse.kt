package com.finpesfatecsp.apikotlin.rest.response

data class BalanceByAccountResponse(
    val id: Long,
    val name: String,
    val balance: Double,
    val status: String,
)
