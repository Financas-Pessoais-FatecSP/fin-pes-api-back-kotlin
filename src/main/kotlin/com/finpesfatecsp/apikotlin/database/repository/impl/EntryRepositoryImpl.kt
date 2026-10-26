package com.finpesfatecsp.apikotlin.database.repository.impl

import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import com.finpesfatecsp.apikotlin.database.jpa.EntryJpaRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryRepository
import org.springframework.stereotype.Repository

@Repository
class EntryRepositoryImpl(
    private val entryJpaRepository: EntryJpaRepository,
) : EntryRepository {
    override fun save(entry: EntryEntity): EntryEntity = entryJpaRepository.save(entry)

    override fun findByAccountId(accountId: Long): List<EntryEntity> = entryJpaRepository.findByAccountId(accountId)

    override fun removeCategory(categoryId: Long) {
        entryJpaRepository
            .findByCategoryId(categoryId)
            .forEach { entry ->
                entryJpaRepository.save(entry.apply { entry.categoryId = null })
            }
    }
}
