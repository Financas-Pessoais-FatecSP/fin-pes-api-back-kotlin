package com.finpesfatecsp.apikotlin.business.usecase.dto

data class BalanceByAccountDTO(
    val id: Long,
    val name: String,
    val balance: Double,
    val status: String,
)
