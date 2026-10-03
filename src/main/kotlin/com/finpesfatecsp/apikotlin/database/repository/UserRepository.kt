package com.finpesfatecsp.apikotlin.database.repository

import com.finpesfatecsp.apikotlin.database.entities.UserEntity

interface UserRepository {
    fun save(user: UserEntity): UserEntity

    fun findById(id: Long): UserEntity?

    fun findByEmail(email: String): UserEntity?
}
