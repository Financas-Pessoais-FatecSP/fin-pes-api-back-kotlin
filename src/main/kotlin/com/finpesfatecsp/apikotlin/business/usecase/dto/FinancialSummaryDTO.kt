package com.finpesfatecsp.apikotlin.business.usecase.dto

data class FinancialSummaryDTO(
    val revenues: List<EntryDTO>,
    val expenses: List<EntryDTO>,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balance: Double,
)
