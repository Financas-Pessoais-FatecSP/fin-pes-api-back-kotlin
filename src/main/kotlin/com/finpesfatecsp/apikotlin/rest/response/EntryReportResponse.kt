package com.finpesfatecsp.apikotlin.rest.response

data class EntryReportResponse(
    val revenues: List<EntryResponse>,
    val expenses: List<EntryResponse>,
    val revenuesByCategory: Map<String, EntriesByCategoryResponse>,
    val expensesByCategory: Map<String, EntriesByCategoryResponse>,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balance: Double,
)
