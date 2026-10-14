package com.finpesfatecsp.apikotlin.database.repository.impl

import com.finpesfatecsp.apikotlin.database.entities.AccountEntity
import com.finpesfatecsp.apikotlin.database.jpa.AccountJpaRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.AccountRepository
import org.springframework.stereotype.Repository

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
) : AccountRepository {
    override fun findById(id: Long): AccountEntity? = accountJpaRepository.findById(id).orElse(null)

    override fun findByUserId(userId: Long): List<AccountEntity> = accountJpaRepository.findByUserId(userId)

    override fun save(account: AccountEntity): AccountEntity = accountJpaRepository.save(account)

    override fun update(account: AccountEntity): AccountEntity = accountJpaRepository.save(account)

    override fun deleteById(id: Long) {
        accountJpaRepository.deleteById(id)
    }
}
