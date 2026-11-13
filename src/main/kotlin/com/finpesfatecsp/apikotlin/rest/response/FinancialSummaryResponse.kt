package com.finpesfatecsp.apikotlin.rest.response

data class FinancialSummaryResponse(
    val balance: Double,
    val totalRevenues: Double,
    val totalExpenses: Double,
    val balanceByAccount: List<BalanceByAccountResponse>,
    val recentEntries: List<FinancialSummaryEntryResponse>,
)
