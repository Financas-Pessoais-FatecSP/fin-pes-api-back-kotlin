package com.finpesfatecsp.apikotlin.rest.response

data class FinancialSummaryResponse(
    val revenues: List<EntryResponse>,
    val expenses: List<EntryResponse>,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balance: Double,
)
