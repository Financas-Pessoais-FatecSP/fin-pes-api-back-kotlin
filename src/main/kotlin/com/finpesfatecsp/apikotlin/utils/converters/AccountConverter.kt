package com.finpesfatecsp.apikotlin.utils.converters

import com.finpesfatecsp.apikotlin.business.usecase.dto.AccountDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.AccountStatus
import com.finpesfatecsp.apikotlin.database.entities.AccountEntity
import com.finpesfatecsp.apikotlin.rest.response.AccountResponse

class AccountConverter {
    companion object {
        fun toAccountDTO(account: AccountEntity): AccountDTO =
            AccountDTO(
                id = account.id ?: throw Exception("Account ID is null"),
                userId = account.userId,
                name = account.name,
                status = AccountStatus.valueOf(account.status.name),
            )

        fun toResponse(account: AccountDTO): AccountResponse =
            AccountResponse(
                id = account.id,
                name = account.name,
                status = account.status.description,
            )
    }
}
