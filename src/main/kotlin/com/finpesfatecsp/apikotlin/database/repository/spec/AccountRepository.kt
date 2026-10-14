package com.finpesfatecsp.apikotlin.database.repository.spec

import com.finpesfatecsp.apikotlin.database.entities.AccountEntity

interface AccountRepository {
    fun findById(id: Long): AccountEntity?

    fun findByUserId(userId: Long): List<AccountEntity>

    fun save(account: AccountEntity): AccountEntity

    fun update(account: AccountEntity): AccountEntity

    fun deleteById(id: Long)
}
