package com.finpesfatecsp.apikotlin.business.usecase.dto

data class EntriesByCategoryDTO(
    val entries: List<EntryDTO>,
    val totalAmount: Double,
)
