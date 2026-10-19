package com.finpesfatecsp.apikotlin.rest.response

data class CreateEntryResponse(
    val id: Long,
    val name: String,
    val entries: List<EntryResponse>,
)
