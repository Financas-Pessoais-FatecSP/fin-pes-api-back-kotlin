package com.finpesfatecsp.apikotlin.database.jpa

import com.finpesfatecsp.apikotlin.database.entities.EntryCategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface EntryCategoryJpaRepository : JpaRepository<EntryCategoryEntity, Long> {
    fun findByUserId(userId: Long): List<EntryCategoryEntity>
}
