package com.finpesfatecsp.apikotlin.database.jpa

import com.finpesfatecsp.apikotlin.database.entities.EntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface EntryJpaRepository : JpaRepository<EntryEntity, Long> {
    fun findByAccountId(accountId: Long): List<EntryEntity>

    fun findByCategoryId(categoryId: Long): List<EntryEntity>

    @Query(
        """
        select entry
        from EntryEntity entry
        inner join AccountEntity account on entry.accountId = account.id
        inner join UserEntity user on account.userId = user.id
        where user.id = :userId
            """,
    )
    fun findByUserId(userId: Long): List<EntryEntity>
}
