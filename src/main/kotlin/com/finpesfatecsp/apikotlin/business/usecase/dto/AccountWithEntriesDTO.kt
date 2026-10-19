package com.finpesfatecsp.apikotlin.business.usecase.dto

data class AccountWithEntriesDTO(
    val id: Long,
    val userId: Long,
    val name: String,
    val entries: List<EntryDTO>,
)
