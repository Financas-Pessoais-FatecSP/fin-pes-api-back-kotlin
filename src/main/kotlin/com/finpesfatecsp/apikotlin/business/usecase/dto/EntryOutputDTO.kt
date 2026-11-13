package com.finpesfatecsp.apikotlin.business.usecase.dto

import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import java.time.LocalDate

data class EntryOutputDTO(
    val id: Long?,
    val account: AccountDTO,
    val category: EntryCategoryDTO?,
    val type: EntryType,
    val description: String,
    val amount: Double,
    val date: LocalDate,
    val details: String?,
)
