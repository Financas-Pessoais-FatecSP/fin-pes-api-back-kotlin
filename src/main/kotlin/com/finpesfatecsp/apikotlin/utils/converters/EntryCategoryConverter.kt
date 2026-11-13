package com.finpesfatecsp.apikotlin.utils.converters

import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryCategoryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.database.entities.EntryCategoryEntity
import com.finpesfatecsp.apikotlin.rest.response.EntryCategoryResponse

class EntryCategoryConverter {
    companion object {
        fun toEntryCategoryDTO(category: EntryCategoryEntity): EntryCategoryDTO =
            EntryCategoryDTO(
                id = category.id,
                userId = category.userId,
                name = category.name,
                type = EntryType.valueOf(category.type.name),
            )

        fun toResponse(category: EntryCategoryDTO): EntryCategoryResponse =
            EntryCategoryResponse(
                id = category.id ?: throw Exception("Entry Category ID is null"),
                name = category.name,
                type = category.type.description,
            )
    }
}
