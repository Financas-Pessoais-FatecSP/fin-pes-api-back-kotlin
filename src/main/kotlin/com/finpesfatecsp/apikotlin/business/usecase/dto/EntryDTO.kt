package com.finpesfatecsp.apikotlin.business.usecase.dto

import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import java.time.LocalDate

data class EntryDTO(
    val id: Long?,
    val accountId: Long,
    val categoryId: Long?,
    val type: EntryType,
    val description: String,
    val amount: Double,
    val date: LocalDate,
    val details: String?,
)
