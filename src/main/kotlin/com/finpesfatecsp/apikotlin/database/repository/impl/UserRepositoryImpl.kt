package com.finpesfatecsp.apikotlin.database.repository

import com.finpesfatecsp.apikotlin.database.entities.UserEntity
import com.finpesfatecsp.apikotlin.database.jpa.UserJpaRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun save(user: UserEntity): UserEntity = userJpaRepository.save(user)

    override fun findById(id: Long): UserEntity? = userJpaRepository.findById(id).orElse(null)

    override fun findByEmail(email: String): UserEntity? = userJpaRepository.findByEmail(email)
}
