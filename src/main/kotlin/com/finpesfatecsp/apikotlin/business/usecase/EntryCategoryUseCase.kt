package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryCategoryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.database.entities.EntryCategoryEntity
import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import org.springframework.stereotype.Component

@Component
class EntryCategoryUseCase(
    private val entryCategoryRepository: EntryCategoryRepository,
    private val entryRepository: EntryRepository,
) {
    fun createEntryCategory(entryCategory: EntryCategoryDTO): EntryCategoryDTO {
        val userCategories = entryCategoryRepository.findByUserId(entryCategory.userId)

        if (userCategories.any { it.name == entryCategory.name }) {
            throw BusinessException(
                message = "User already have an entry category with name '${entryCategory.name}'",
                code = "entry.category.already.exists",
            )
        }

        val categoryToSave =
            EntryCategoryEntity(
                id = null,
                userId = entryCategory.userId,
                name = entryCategory.name,
                type = EntryTypeEnum.valueOf(entryCategory.type.name),
            )

        return entryCategoryRepository.save(categoryToSave).let {
            EntryCategoryDTO(
                id = it.id,
                userId = it.userId,
                name = it.name,
                type = EntryType.valueOf(it.type.name),
            )
        }
    }

    fun findByUserId(userId: Long): List<EntryCategoryDTO> =
        entryCategoryRepository.findByUserId(userId).map {
            EntryCategoryDTO(
                id = it.id,
                userId = it.userId,
                name = it.name,
                type = EntryType.valueOf(it.type.name),
            )
        }

    fun deleteById(
        userId: Long,
        categoryId: Long,
    ) {
        val userCategories = entryCategoryRepository.findByUserId(userId)

        if (userCategories.none { it.id == categoryId }) {
            throw BusinessException(
                message = "User cannot delete categories for an account that does not belong to them",
                code = "user.not.owner",
            )
        }

        entryRepository.removeCategory(categoryId)

        entryCategoryRepository.deleteById(categoryId)
    }
}
