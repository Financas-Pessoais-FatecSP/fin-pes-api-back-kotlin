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

        if (userAccounts.find { it.name == name } != null) {
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
}
