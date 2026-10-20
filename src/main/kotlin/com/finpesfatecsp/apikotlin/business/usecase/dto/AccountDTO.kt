package com.finpesfatecsp.apikotlin.business.usecase.dto

import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.AccountStatus

data class AccountDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val status: AccountStatus,
)
