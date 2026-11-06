package com.finpesfatecsp.apikotlin.business.usecase.dto

data class EntryReportDTO(
    val revenues: List<EntryDTO>,
    val expenses: List<EntryDTO>,
    val revenuesByCategory: Map<String, EntriesByCategoryDTO>,
    val expensesByCategory: Map<String, EntriesByCategoryDTO>,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balance: Double,
)
