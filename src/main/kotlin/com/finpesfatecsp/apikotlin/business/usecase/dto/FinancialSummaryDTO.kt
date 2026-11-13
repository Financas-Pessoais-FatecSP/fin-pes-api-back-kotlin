package com.finpesfatecsp.apikotlin.business.usecase.dto

data class FinancialSummaryDTO(
    val balance: Double,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balanceByAccount: List<BalanceByAccountDTO>,
    val recentEntries: List<EntryOutputDTO>,
)
