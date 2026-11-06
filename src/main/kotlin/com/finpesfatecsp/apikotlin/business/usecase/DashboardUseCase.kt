package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.FinancialSummaryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DashboardUseCase(
    private val entryRepository: EntryRepository,
    private val entryCategoryRepository: EntryCategoryRepository,
) {
    companion object {
        const val EMPTY_CATEGORY = "Empty"
    }

    fun getFinancialSummary(
        userId: Long,
        from: LocalDate,
        to: LocalDate,
    ): FinancialSummaryDTO {
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
                .groupBy { it.categoryId }
                .map { category ->
                    Pair(
                        userCategories[category.key] ?: EMPTY_CATEGORY,
                        category.value.sumOf { it.amount },
                    )
                }.toMap()

        val expensesByCategory =
            expenses
                .groupBy { it.categoryId }
                .map { category ->
                    Pair(
                        userCategories[category.key] ?: EMPTY_CATEGORY,
                        category.value.sumOf { it.amount },
                    )
                }.toMap()

        return FinancialSummaryDTO(
            revenues =
                revenues.map {
                    EntryDTO(
                        id = it.id,
                        accountId = it.accountId,
                        categoryId = it.categoryId,
                        type = EntryType.valueOf(it.type.name),
                        description = it.description,
                        amount = it.amount,
                        date = it.date,
                        details = it.details,
                    )
                },
            expenses =
                expenses.map {
                    EntryDTO(
                        id = it.id,
                        accountId = it.accountId,
                        categoryId = it.categoryId,
                        type = EntryType.valueOf(it.type.name),
                        description = it.description,
                        amount = it.amount,
                        date = it.date,
                        details = it.details,
                    )
                },
            revenuesByCategory = revenuesByCategory,
            expensesByCategory = expensesByCategory,
            totalRevenues = totalRevenues,
            totalExpenses = totalExpenses,
            balance = balance,
        )
    }
}
