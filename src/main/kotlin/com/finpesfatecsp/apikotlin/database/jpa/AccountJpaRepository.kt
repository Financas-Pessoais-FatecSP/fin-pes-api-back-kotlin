package com.finpesfatecsp.apikotlin.database.jpa

import com.finpesfatecsp.apikotlin.database.entities.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AccountJpaRepository : JpaRepository<AccountEntity, Long> {
    fun findByUserId(userId: Long): List<AccountEntity>
}
