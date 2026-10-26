package com.finpesfatecsp.apikotlin.database.jpa

import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EntryJpaRepository : JpaRepository<EntryEntity, Long> {
    fun findByAccountId(accountId: Long): List<EntryEntity>

    fun findByCategoryId(categoryId: Long): List<EntryEntity>
}
