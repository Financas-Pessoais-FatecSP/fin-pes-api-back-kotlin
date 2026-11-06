package com.finpesfatecsp.apikotlin.rest.response

data class FinancialSummaryResponse(
    val revenues: List<EntryResponse>,
    val expenses: List<EntryResponse>,
    val revenuesByCategory: Map<String, Double>,
    val expensesByCategory: Map<String, Double>,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balance: Double,
)
