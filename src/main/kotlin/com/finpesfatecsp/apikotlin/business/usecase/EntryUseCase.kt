package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import com.finpesfatecsp.apikotlin.business.usecase.dto.AccountWithEntriesDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.AccountStatus
import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import com.finpesfatecsp.apikotlin.database.repository.spec.AccountRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import com.finpesfatecsp.apikotlin.utils.converters.EntryConverter
import org.springframework.stereotype.Component

@Component
class EntryUseCase(
    private val entryRepository: EntryRepository,
    private val accountsRepository: AccountRepository,
    private val entryCategoryRepository: EntryCategoryRepository,
) {
    fun createEntry(
        userId: Long,
        entry: EntryDTO,
    ): AccountWithEntriesDTO {
        val userAccounts = accountsRepository.findByUserId(userId)

        if (userAccounts.none { it.id == entry.accountId }) {
            throw BusinessException(
                message = "User cannot add an entry to an account that does not belong to them",
                code = "user.not.owner",
            )
        }

        if (entry.amount < 0.0) {
            throw BusinessException(
                message = "Entry amount cannot be less than zero",
                code = "entry.less.than.zero",
            )
        }

        val entryToSave =
            EntryEntity(
                id = null,
                account = userAccounts.first { it.id == entry.accountId },
                category = entry.categoryId?.let { entryCategoryRepository.findById(it) },
                type = EntryTypeEnum.valueOf(entry.type.name),
                description = entry.description,
                amount = entry.amount,
                date = entry.date,
                details = entry.details,
            )

        entryRepository.save(entryToSave)

        return accountsRepository
            .findByUserId(userId)
            .first { it.id == entry.accountId }
            .let { account ->
                AccountWithEntriesDTO(
                    id = account.id ?: throw Exception("Account ID is null"),
                    userId = account.userId,
                    name = account.name,
                    status = AccountStatus.valueOf(account.status.name),
                    entries = entryRepository.findByAccountId(entry.accountId).map { EntryConverter.toEntryDTO(it) },
                )
            }
    }

    fun findByAccountId(
        userId: Long,
        accountId: Long,
    ): AccountWithEntriesDTO {
        val userAccounts = accountsRepository.findByUserId(userId)

        if (userAccounts.none { it.id == accountId }) {
            throw BusinessException(
                message = "User cannot view entries for an account that does not belong to them",
                code = "user.not.owner",
            )
        }

        return userAccounts.first { it.id == accountId }.let { account ->
            AccountWithEntriesDTO(
                id = account.id ?: throw Exception("Account ID is null"),
                userId = account.userId,
                name = account.name,
                status = AccountStatus.valueOf(account.status.name),
                entries = entryRepository.findByAccountId(accountId).map { EntryConverter.toEntryDTO(it) },
            )
        }
    }

    fun deleteEntry(
        userId: Long,
        entryId: Long,
    ) {
        val entry =
            entryRepository.findById(entryId)
                ?: throw BusinessException(
                    message = "Entry not found",
                    code = "entry.not.found",
                )

        val account = accountsRepository.findByUserId(userId)

        if (account.none { it.id == entry.account.id }) {
            throw BusinessException(
                message = "User cannot delete an entry from an account that does not belong to them",
                code = "user.not.owner",
            )
        }

        entryRepository.deleteById(entryId)
    }
}
