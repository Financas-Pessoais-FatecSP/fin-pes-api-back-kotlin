package com.finpesfatecsp.apikotlin.database.repository.spec

import com.finpesfatecsp.apikotlin.database.entities.EntryEntity

interface EntryRepository {
    fun save(entry: EntryEntity): EntryEntity

    fun findByAccountId(accountId: Long): List<EntryEntity>
}
