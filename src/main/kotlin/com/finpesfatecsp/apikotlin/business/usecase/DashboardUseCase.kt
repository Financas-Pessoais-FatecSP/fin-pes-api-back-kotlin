package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.usecase.dto.BalanceByAccountDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.FinancialSummaryDTO
import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import com.finpesfatecsp.apikotlin.database.repository.spec.AccountRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import com.finpesfatecsp.apikotlin.utils.converters.EntryConverter
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DashboardUseCase(
    private val entryRepository: EntryRepository,
    private val entryCategoryRepository: EntryCategoryRepository,
    private val accountRepository: AccountRepository,
) {
    companion object {
        const val EMPTY_ACCOUNT = "Empty"
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

        val totalRevenues =
            userEntries.filter { it.type == EntryTypeEnum.REVENUE }.map { it.amount }.reduceOrNull { result, amount -> result + amount }
                ?: 0.0
        val totalExpenses =
            userEntries.filter { it.type == EntryTypeEnum.EXPENSE }.map { it.amount }.reduceOrNull { result, amount -> result + amount }
                ?: 0.0

        val balance = totalRevenues - totalExpenses

        val userAccounts = accountRepository.findByUserId(userId)

        val idAndNameOfUserAccounts = userAccounts.associate { it.id to it.name }

        val balanceByAccount =
            userEntries
                .groupBy { it.account.id }
                .map { account ->
                    BalanceByAccountDTO(
                        id = account.key ?: throw Exception("Account ID, is null"),
                        name = idAndNameOfUserAccounts[account.key] ?: EMPTY_ACCOUNT,
                        balance =
                            account.value.filter { it.type == EntryTypeEnum.REVENUE }.sumOf { it.amount } -
                                account.value.filter { it.type == EntryTypeEnum.EXPENSE }.sumOf { it.amount },
                        status = userAccounts.first { it.id == account.key }.status.name,
                    )
                }

        return FinancialSummaryDTO(
            balance = balance,
            totalRevenues = totalRevenues,
            totalExpenses = totalExpenses,
            balanceByAccount = balanceByAccount,
            recentEntries = userEntries.map { EntryConverter.toEntryOutputDTO(it) },
        )
    }
}
