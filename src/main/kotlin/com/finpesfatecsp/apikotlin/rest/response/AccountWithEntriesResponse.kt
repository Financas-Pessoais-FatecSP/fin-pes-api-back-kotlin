package com.finpesfatecsp.apikotlin.rest.response

data class AccountWithEntriesResponse(
    val accountId: Long,
    val accountName: String,
    val accountStatus: String,
    val accountEntries: List<EntryResponse>,
)
