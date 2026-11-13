package com.finpesfatecsp.apikotlin.utils.converters

import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryOutputDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import com.finpesfatecsp.apikotlin.rest.response.EntryResponse
import com.finpesfatecsp.apikotlin.rest.response.FinancialSummaryEntryResponse

class EntryConverter {
    companion object {
        fun toEntryDTO(entry: EntryEntity): EntryDTO =
            EntryDTO(
                id = entry.id,
                accountId = entry.account.id ?: throw Exception("Account ID is null"),
                categoryId = entry.category?.id,
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

        fun toEntryOutputDTO(entry: EntryEntity): EntryOutputDTO =
            EntryOutputDTO(
                id = entry.id,
                account = AccountConverter.toAccountDTO(entry.account),
                category = entry.category?.let { EntryCategoryConverter.toEntryCategoryDTO(it) },
                type = EntryType.valueOf(entry.type.name),
                description = entry.description,
                amount = entry.amount,
                date = entry.date,
                details = entry.details,
            )

        fun toFinancialSummaryEntryResponse(entry: EntryOutputDTO): FinancialSummaryEntryResponse =
            FinancialSummaryEntryResponse(
                id = entry.id,
                account = AccountConverter.toResponse(entry.account),
                category = entry.category?.let { EntryCategoryConverter.toResponse(it) },
                type = entry.type.description,
                description = entry.description,
                amount = entry.amount,
                date = entry.date,
                details = entry.details,
            )
    }
}
