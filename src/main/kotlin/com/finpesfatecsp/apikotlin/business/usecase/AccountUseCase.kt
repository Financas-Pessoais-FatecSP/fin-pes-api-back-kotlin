package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import com.finpesfatecsp.apikotlin.business.usecase.dto.AccountDTO
import com.finpesfatecsp.apikotlin.database.entities.AccountEntity
import com.finpesfatecsp.apikotlin.database.repository.spec.AccountRepository
import org.springframework.stereotype.Component

@Component
class AccountUseCase(
    private val accountRepository: AccountRepository,
) {
    fun createAccount(
        userId: Long,
        name: String,
    ): List<AccountDTO> {
        val userAccounts = accountRepository.findByUserId(userId)

        if (userAccounts.any { it.name == name }) {
            throw BusinessException(
                message = "User already have an account with name '$name'",
                code = "account.already.exists",
            )
        }

        accountRepository.save(
            AccountEntity(
                id = null,
                userId = userId,
                name = name,
            ),
        )

        return accountRepository.findByUserId(userId).map {
            AccountDTO(
                id = it.id ?: throw Exception("Account ID is null"),
                userId = it.userId,
                name = it.name,
            )
        }
    }

    fun findByUserId(userId: Long): List<AccountDTO> =
        accountRepository.findByUserId(userId).map {
            AccountDTO(
                id = it.id ?: throw Exception("Account ID is null"),
                userId = it.userId,
                name = it.name,
            )
        }

    fun updateAccount(
        userId: Long,
        accountId: Long,
        newAccountName: String,
    ): List<AccountDTO> {
        val userAccounts = accountRepository.findByUserId(userId)

        if (userAccounts.none { it.id == accountId }) {
            throw BusinessException(
                message = "User cannot update an account that does not belong to them",
                code = "user.not.owner",
            )
        }

        if (userAccounts.any { it.name == newAccountName }) {
            throw BusinessException(
                message = "User already have an account with name '$newAccountName'",
                code = "account.already.exists",
            )
        }

        accountRepository.save(
            AccountEntity(
                id = accountId,
                userId = userId,
                name = newAccountName,
            ),
        )

        return accountRepository.findByUserId(userId).map {
            AccountDTO(
                id = it.id ?: throw Exception("Account ID is null"),
                userId = it.userId,
                name = it.name,
            )
        }
    }
}
