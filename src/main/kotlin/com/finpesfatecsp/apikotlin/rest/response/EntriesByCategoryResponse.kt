package com.finpesfatecsp.apikotlin.rest.response

data class EntriesByCategoryResponse(
    val entries: List<EntryResponse>,
    val totalAmount: Double,
)
