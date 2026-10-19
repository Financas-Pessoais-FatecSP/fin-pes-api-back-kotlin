package com.finpesfatecsp.apikotlin.rest.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate

data class EntryResponse(
    val id: Long,
    val accountId: Long,
    val categoryId: Long?,
    val type: String,
    val description: String,
    val amount: Double,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val date: LocalDate,
    val details: String?,
)
