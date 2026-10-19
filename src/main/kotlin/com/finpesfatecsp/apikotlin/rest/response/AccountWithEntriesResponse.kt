package com.finpesfatecsp.apikotlin.rest.response

data class AccountWithEntriesResponse(
    val id: Long,
    val name: String,
    val entries: List<EntryResponse>,
)
