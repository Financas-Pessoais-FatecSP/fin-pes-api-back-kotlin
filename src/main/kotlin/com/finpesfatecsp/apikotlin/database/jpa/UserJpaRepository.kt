package com.finpesfatecsp.apikotlin.database.jpa

import com.finpesfatecsp.apikotlin.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByEmail(email: String): UserEntity?
}
