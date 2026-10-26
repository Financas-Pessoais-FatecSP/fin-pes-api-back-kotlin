package com.finpesfatecsp.apikotlin.database.repository.impl

import com.finpesfatecsp.apikotlin.database.entities.EntryCategoryEntity
import com.finpesfatecsp.apikotlin.database.jpa.EntryCategoryJpaRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.EntryCategoryRepository
import org.springframework.stereotype.Repository

@Repository
class EntryCategoryRepositoryImpl(
    private val entryCategoryJpaRepository: EntryCategoryJpaRepository,
) : EntryCategoryRepository {
    override fun save(entryCategory: EntryCategoryEntity): EntryCategoryEntity = entryCategoryJpaRepository.save(entryCategory)

    override fun findByUserId(userId: Long): List<EntryCategoryEntity> = entryCategoryJpaRepository.findByUserId(userId)

    override fun deleteById(entryCategoryId: Long) {
        entryCategoryJpaRepository.deleteById(entryCategoryId)
    }
}
