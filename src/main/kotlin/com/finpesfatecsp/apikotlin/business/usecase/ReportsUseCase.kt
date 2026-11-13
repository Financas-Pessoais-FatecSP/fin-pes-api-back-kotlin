package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.usecase.dto.EntriesByCategoryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryReportDTO
import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import com.finpesfatecsp.apikotlin.utils.converters.EntryConverter
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ReportsUseCase(
    private val entryRepository: EntryRepository,
    private val entryCategoryRepository: EntryCategoryRepository,
) {
    companion object {
        const val EMPTY_CATEGORY = "Empty"
    }

    fun getEntryReportByDate(
        userId: Long,
        from: LocalDate,
        to: LocalDate,
    ): EntryReportDTO {
        val userEntries =
            entryRepository
                .findByUserId(userId)
                .filter {
                    (it.date.isEqual(from) || it.date.isAfter(from)) &&
                        (it.date.isEqual(to) || it.date.isBefore(to))
                }

        val revenues = userEntries.filter { it.type == EntryTypeEnum.REVENUE }
        val expenses = userEntries.filter { it.type == EntryTypeEnum.EXPENSE }

        val totalRevenues = revenues.map { it.amount }.reduceOrNull { result, amount -> result + amount } ?: 0.0
        val totalExpenses = expenses.map { it.amount }.reduceOrNull { result, amount -> result + amount } ?: 0.0

        val balance = totalRevenues - totalExpenses

        val userCategories = entryCategoryRepository.findByUserId(userId).associate { it.id to it.name }

        val revenuesByCategory =
            revenues
                .groupBy { it.category?.id }
                .map { category ->
                    Pair(
                        userCategories[category.key] ?: EMPTY_CATEGORY,
                        category.value,
                    )
                }.toMap()

        val expensesByCategory =
            expenses
                .groupBy { it.category?.id }
                .map { category ->
                    Pair(
                        userCategories[category.key] ?: EMPTY_CATEGORY,
                        category.value,
                    )
                }.toMap()

        return EntryReportDTO(
            revenues = revenues.map { EntryConverter.toEntryDTO(it) },
            expenses = expenses.map { EntryConverter.toEntryDTO(it) },
            revenuesByCategory =
                revenuesByCategory
                    .map { category ->
                        category.key to
                            EntriesByCategoryDTO(
                                entries = category.value.map { EntryConverter.toEntryDTO(it) },
                                totalAmount = category.value.sumOf { it.amount },
                            )
                    }.toMap(),
            expensesByCategory =
                expensesByCategory
                    .map { category ->
                        category.key to
                            EntriesByCategoryDTO(
                                entries = category.value.map { EntryConverter.toEntryDTO(it) },
                                totalAmount = category.value.sumOf { it.amount },
                            )
                    }.toMap(),
            totalRevenues = totalRevenues,
            totalExpenses = totalExpenses,
            balance = balance,
        )
    }
}
