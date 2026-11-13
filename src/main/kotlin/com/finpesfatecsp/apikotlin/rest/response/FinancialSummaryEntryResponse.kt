package com.finpesfatecsp.apikotlin.rest.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class FinancialSummaryEntryResponse(
    val id: Long?,
    val account: AccountResponse,
    val category: EntryCategoryResponse?,
    val type: String,
    val description: String,
    val amount: Double,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val details: String?,
)
