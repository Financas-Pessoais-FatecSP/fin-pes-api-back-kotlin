package com.finpesfatecsp.apikotlin.utils.converters

import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import com.finpesfatecsp.apikotlin.rest.response.EntryResponse

class EntryConverter {
    companion object {
        fun toEntryDTO(entry: EntryEntity): EntryDTO =
            EntryDTO(
                id = entry.id,
                accountId = entry.accountId,
                categoryId = entry.categoryId,
                type = EntryType.valueOf(entry.type.name),
                description = entry.description,
                amount = entry.amount,
                date = entry.date,
                details = entry.details,
            )

        fun toResponse(entry: EntryDTO): EntryResponse =
            EntryResponse(
                id = entry.id ?: throw Exception("Entry ID is null"),
                accountId = entry.accountId,
                categoryId = entry.categoryId,
                type = entry.type.description,
                description = entry.description,
                amount = entry.amount,
                date = entry.date,
                details = entry.details,
            )
    }
}
