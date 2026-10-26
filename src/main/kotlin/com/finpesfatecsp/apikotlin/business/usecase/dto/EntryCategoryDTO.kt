package com.finpesfatecsp.apikotlin.business.usecase.dto

import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType

data class EntryCategoryDTO(
    val id: Long?,
    val userId: Long,
    val name: String,
    val type: EntryType,
)
