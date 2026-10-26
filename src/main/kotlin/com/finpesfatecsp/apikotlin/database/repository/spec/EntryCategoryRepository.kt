package com.finpesfatecsp.apikotlin.database.repository.spec

import com.finpesfatecsp.apikotlin.database.entities.EntryCategoryEntity

interface EntryCategoryRepository {
    fun save(entryCategory: EntryCategoryEntity): EntryCategoryEntity

    fun findByUserId(userId: Long): List<EntryCategoryEntity>

    fun deleteById(entryCategoryId: Long)
}
